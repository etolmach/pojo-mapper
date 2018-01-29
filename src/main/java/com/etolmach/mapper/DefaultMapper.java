package com.etolmach.mapper;

import com.etolmach.mapper.converter.ConverterByNameProvider;
import com.etolmach.mapper.converter.ConverterByTypeProvider;
import com.etolmach.mapper.exceptions.CannotInjectValueException;
import com.etolmach.mapper.exceptions.CannotInstantiateDestinationObjectException;
import com.etolmach.mapper.exceptions.CannotRetrieveSourceValueException;
import org.apache.commons.jxpath.util.TypeConverter;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author etolmach
 */
public class DefaultMapper<S, D> implements Mapper<S, D> {

    // destMember -> srcMember
    private final List<MappingDetails> mappingDetailsList;

    private final Class<S> srcClass;
    private final Class<D> destClass;
    private final ConverterByTypeProvider converterByTypeProvider;
    private final ConverterByNameProvider converterByNameProvider;

    DefaultMapper(Class<S> srcClass, Class<D> destClass, List<MappingDetails> mappingDetailsList,
                  ConverterByTypeProvider converterByTypeProvider, ConverterByNameProvider converterByNameProvider) {
        this.srcClass = srcClass;
        this.destClass = destClass;
        this.mappingDetailsList = mappingDetailsList;
        this.converterByTypeProvider = converterByTypeProvider;
        this.converterByNameProvider = converterByNameProvider;
    }

    @Override
    public D map(S srcObject) throws CannotInjectValueException, CannotRetrieveSourceValueException, CannotInstantiateDestinationObjectException {
        try {
            D destObject = destClass.newInstance();
            return map(srcObject, destObject);
        } catch (ReflectiveOperationException e) {
            throw new CannotInstantiateDestinationObjectException(destClass, e);
        }
    }

    @Override
    public D map(S srcObject, D destObject) throws CannotRetrieveSourceValueException, CannotInjectValueException {
        for (MappingDetails details : mappingDetailsList) {
            // Retrieve the value from the source
            Object srcValue = retrieveValue(srcObject, details.getSrcMember());
            // Convert the value to the target type
            Object convertedValue = convertValue(srcValue, details.getDestMemberType(), details.getConverter());
            // Inject the value to the destination
            injectValue(destObject, details.getSrcMember(), convertedValue);
        }
        return destObject;
    }

    private Object retrieveValue(Object srcObject, Member srcMember) throws CannotRetrieveSourceValueException {
        Object value = null;
        try {
            if (srcMember instanceof Field) {
                value = ((Field) srcMember).get(srcObject);
            } else if (srcMember instanceof Method) {
                // Current version supports parameter-less source methods only
                value = ((Method) srcMember).invoke(srcObject);
            }
        } catch (ReflectiveOperationException e) {
            throw new CannotRetrieveSourceValueException(srcClass, srcMember, e);
        }
        return value;
    }

    private Object convertValue(Object srcValue, Class<?> destMemberType, TypeConverter converter) {
        if (converter == null) {
            // No type converter is defined. Return unchanged srcValue
            return srcValue;
        } else {
            return converter.convert(srcValue, destMemberType);
        }
    }

    private void injectValue(D destObject, Member destMember, Object value) throws CannotInjectValueException {
        try {
            if (destMember instanceof Field) {
                ((Field) destMember).set(destObject, value);
            } else if (destMember instanceof Method) {
                // Current version supports  single-parameter destination methods only
                ((Method) destMember).invoke(destObject, value);
            }
        } catch (ReflectiveOperationException e) {
            throw new CannotInjectValueException(srcClass, destMember, e);
        }
    }
}
