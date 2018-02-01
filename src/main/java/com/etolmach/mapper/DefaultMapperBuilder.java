package com.etolmach.mapper;

import com.etolmach.mapper.annotations.FieldMapping;
import com.etolmach.mapper.annotations.MethodMapping;
import com.etolmach.mapper.converter.CachedConverterByTypeProvider;
import com.etolmach.mapper.converter.ConverterByNameProvider;
import com.etolmach.mapper.converter.ConverterByTypeProvider;
import com.etolmach.mapper.exceptions.*;
import org.apache.commons.jxpath.util.TypeConverter;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author etolmach
 */
public class DefaultMapperBuilder implements MapperBuilder {

    protected final ConverterByTypeProvider converterByTypeProvider;
    protected final ConverterByNameProvider converterByNameProvider;

    public DefaultMapperBuilder() {
        this(new CachedConverterByTypeProvider(), null);
    }

    public DefaultMapperBuilder(ConverterByTypeProvider converterByTypeProvider, ConverterByNameProvider converterByNameProvider) {
        this.converterByTypeProvider = converterByTypeProvider;
        this.converterByNameProvider = converterByNameProvider;
    }

    @Override
    public <S, D> Mapper<S, D> build(S srcObject, D destObject) throws MapperConfigurationException {
        return build((Class<S>) srcObject.getClass(), (Class<D>) destObject.getClass());
    }

    @Override
    public <S, D> Mapper<S, D> build(Class<S> srcClass, Class<D> destClass) throws MapperConfigurationException {
        List<MappingDetails> mappingDetailsList = new ArrayList<>();
        lookForAnnotatedMembers(srcClass, destClass, destClass.getDeclaredFields(), mappingDetailsList);
        lookForAnnotatedMembers(srcClass, destClass, destClass.getDeclaredMethods(), mappingDetailsList);
        return buildFor(srcClass, destClass, mappingDetailsList);
    }

    @Override
    public <D> Map<Class<?>, Mapper<?, D>> buildAll(Class<D> destClass, Class<?>... srcClasses) throws MapperConfigurationException {
        Map<Class<?>, Mapper<?, D>> mappers = new HashMap<>();
        for (Class<?> srcClass : srcClasses) {
            mappers.put(srcClass, build(srcClass, destClass));
        }
        return mappers;
    }

    @Override
    public <D> Map<Class<?>, Mapper<?, D>> buildAll(Class<D> destClass, List<Class<?>> srcClasses) throws MapperConfigurationException {
        return buildAll(destClass, srcClasses.toArray(new Class<?>[srcClasses.size()]));
    }

    protected <S, D> Mapper<S, D> buildFor(Class<S> srcClass, Class<D> destClass, List<MappingDetails> mappingDetailsList) {
        return new DefaultMapper<>(srcClass, destClass, mappingDetailsList);
    }

    private <S, D> void lookForAnnotatedMembers(Class<S> srcClass, Class<D> destClass, Member[] destMembers, List<MappingDetails> mappingDetailsList) throws MultipleMappingAnnotationsException, InvalidSourceMemberException, IncompatibleTypesException, CannotProvideConverterException, MultipleConvertersDefinedException {
        for (Member destMember : destMembers) {
            MappingDetails mappingDetails = retrieveMappingDetails(srcClass, destClass, destMember);
            if (mappingDetails != null) {
                ((AccessibleObject) mappingDetails.getSrcMember()).setAccessible(true);
                ((AccessibleObject) mappingDetails.getDestMember()).setAccessible(true);
                mappingDetailsList.add(mappingDetails);
            }
        }
    }

    private <S, D> MappingDetails retrieveMappingDetails(Class<S> srcClass, Class<D> destClass, Member destMember) throws MultipleMappingAnnotationsException, InvalidSourceMemberException, CannotProvideConverterException, MultipleConvertersDefinedException, IncompatibleTypesException {
        MappingDetails mappingDetails = null;
        MethodMapping methodMapping = ((AnnotatedElement) destMember).getAnnotation(MethodMapping.class);
        FieldMapping fieldMapping = ((AnnotatedElement) destMember).getAnnotation(FieldMapping.class);
        // Check if the name is annotated with @MethodMapping and @FieldMapping
        if (methodMapping != null && fieldMapping != null) {
            throw new MultipleMappingAnnotationsException(destClass, destMember);
        } else if (methodMapping != null) {
            mappingDetails = getMethodMappingDetails(srcClass, destClass, destMember, methodMapping);
        } else if (fieldMapping != null) {
            mappingDetails = getFieldMappingDetails(srcClass, destClass, destMember, fieldMapping);
        }
        return mappingDetails;
    }

    private <S, D> MappingDetails getFieldMappingDetails(Class<S> srcClass, Class<D> destClass, Member destMember, FieldMapping fieldMapping) throws InvalidSourceMemberException, CannotProvideConverterException, MultipleConvertersDefinedException, IncompatibleTypesException {
        Class<?> explicitSrcClass = fieldMapping.source();
        if (explicitSrcClass.isAssignableFrom(srcClass)) {
            TypeConverter converter = retrieveConverter(fieldMapping.converter(), fieldMapping.converterName(), destClass, destMember);
            try {
                Member srcMember = srcClass.getDeclaredField(fieldMapping.name());
                Class<?> destMemberType = getDestinationType(destMember);
                validateTypeCompatibility(srcClass, destClass, destMember, converter, srcMember, destMemberType);
                return new DefaultMappingDetails(srcMember, destMember, destMemberType, converter);
            } catch (NoSuchFieldException e) {
                throw new InvalidSourceMemberException(srcClass, destClass, destMember);
            }
        }
        return null;
    }

    private <S, D> MappingDetails getMethodMappingDetails(Class<S> srcClass, Class<D> destClass, Member destMember, MethodMapping methodMapping) throws InvalidSourceMemberException, CannotProvideConverterException, MultipleConvertersDefinedException, IncompatibleTypesException {
        Class<?> explicitSrcClass = methodMapping.source();
        if (explicitSrcClass.isAssignableFrom(srcClass)) {
            TypeConverter converter = retrieveConverter(methodMapping.converter(), methodMapping.converterName(), destClass, destMember);
            try {
                Member srcMember = srcClass.getDeclaredMethod(methodMapping.name());
                Class<?> destMemberType = getDestinationType(destMember);
                validateTypeCompatibility(srcClass, destClass, destMember, converter, srcMember, destMemberType);
                return new DefaultMappingDetails(srcMember, destMember, destMemberType, converter);
            } catch (NoSuchMethodException e) {
                throw new InvalidSourceMemberException(srcClass, destClass, destMember);
            }
        }
        return null;
    }

    private <S, D> void validateTypeCompatibility(Class<S> srcClass, Class<D> destClass, Member destMember, TypeConverter converter, Member srcMember, Class<?> destMemberType) throws IncompatibleTypesException {
        if (converter == null) {
            // If the converter is not defined, then validate if destination member is pointing to a valid source member
            Class<?> srcMemberType = getSourceType(srcMember);
            if (!srcMemberType.equals(destMemberType)) {
                throw new IncompatibleTypesException(srcClass, srcMember, destClass, destMember);
            }
        }
    }

    private <D> TypeConverter retrieveConverter(Class<? extends TypeConverter> converterClass, String converterName, Class<D> destClass, Member destMember) throws MultipleConvertersDefinedException, CannotProvideConverterException {
        TypeConverter converter = null;
        if (!converterClass.equals(TypeConverter.class) && !converterName.isEmpty()) {
            throw new MultipleConvertersDefinedException(destClass, destMember);
        } else if (!converterClass.equals(TypeConverter.class)) {
            converter = converterByTypeProvider.provide(converterClass);
        } else if (!converterName.isEmpty()) {
            converter = converterByNameProvider.provide(converterName);
        }
        return converter;
    }

    private Class<?> getSourceType(Member member) {
        if (member instanceof Field) {
            return ((Field) member).getType();
        } else if (member instanceof Method) {
            return ((Method) member).getReturnType();
        } else {
            // Practically this line should be unreachable
            throw new RuntimeException("Unknown member type!");
        }
    }

    private Class<?> getDestinationType(Member member) {
        if (member instanceof Field) {
            return ((Field) member).getType();
        } else if (member instanceof Method) {
            return ((Method) member).getParameterTypes()[0];
        } else {
            // Practically this line should be unreachable
            throw new RuntimeException("Unknown member type!");
        }
    }

}