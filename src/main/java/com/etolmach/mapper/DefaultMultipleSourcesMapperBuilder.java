//package com.etolmach.mapper;
//
//import FieldMapping;
//import MethodMapping;
//import IncompatibleTypesException;
//import InvalidSourceMemberException;
//import MapperConfigurationException;
//import MultipleMappingAnnotationsException;
//import lombok.Data;
//
//import java.lang.reflect.*;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author etolmach
// */
//@Data
//public class DefaultMultipleSourcesMapperBuilder {
//
//
//    public <D> DefaultMapper<?, D> build(Object srcObject, D destObject) throws MapperConfigurationException {
//        return build(srcObject.getClass(), (Class<D>) destObject.getClass());
//    }
//
//    public <D> DefaultMapper<?, D> build(Class<?> srcClass, Class<D> destClass) throws MapperConfigurationException {
//        Map<Class<?>, DefaultMapper<?, D>> mappers = buildAll(destClass, srcClass);
//        return mappers.get(srcClass);
////
////        Map<Member, Member> map = new HashMap<>();
////        lookForAnnotatedMembers(srcClass, destClass, destClass.getDeclaredFields(), map);
////        lookForAnnotatedMembers(srcClass, destClass, destClass.getDeclaredMethods(), map);
////        return new DefaultMapper<>(srcClass, destClass, map);
//    }
//
//    public <D> Map<Class<?>, DefaultMapper<?, D>> buildAll(Class<D> destClass, Class<?>... srcClasses) throws MapperConfigurationException {
//        Map<Class<?>, Map<Member, Member>> map = new HashMap<>();
//        lookForAnnotatedMembers(srcClasses, destClass, destClass.getDeclaredFields(), map);
//        lookForAnnotatedMembers(srcClasses, destClass, destClass.getDeclaredMethods(), map);
//        Map<Class<?>, DefaultMapper<?, D>> mappers = new HashMap<>();
//        for (Map.Entry<Class<?>, Map<Member, Member>> entry : map.entrySet()) {
//            Class<?> srcClass = entry.getKey();
//            mappers.put(srcClass, new DefaultMapper<>(srcClass, destClass, entry.getValue()));
//        }
//        return mappers;
//    }
//
//    private <D> void lookForAnnotatedMembers(Class<?>[] srcClasses, Class<D> destClass, Member[] destMembers, Map<Class<?>, Map<Member, Member>> map) throws MultipleMappingAnnotationsException, InvalidSourceMemberException, IncompatibleTypesException {
//        for (Member destMember : destMembers) {
//            for (Class<?> srcClass : srcClasses) {
//                Member srcMember = retrieveSourceMember(srcClass, destClass, destMember);
//                if (srcMember != null) {
//                    // If the destination member is pointing to a valid source member then add it to the map
//                    validateCompatibility(srcClass, srcMember, destClass, destMember);
//                    // Allow using private members
//                    ((AccessibleObject) srcMember).setAccessible(true);
//                    ((AccessibleObject) destMember).setAccessible(true);
//                    // Put the members into map
//                    saveMembers(srcClass, srcMember, destMember, map);
//                }
//            }
//        }
//    }
//
//    private void saveMembers(Class<?> srcClass, Member srcMember, Member destMember, Map<Class<?>, Map<Member, Member>> map) {
//        Map<Member, Member> subMap = map.get(srcClass);
//        if (subMap == null) {
//            subMap = new HashMap<>();
//            map.put(srcClass, subMap);
//        }
//        subMap.put(destMember, srcMember);
//    }
//
//    private <S, D> Member retrieveSourceMember(Class<S> srcClass, Class<D> destClass, Member destMember) throws MultipleMappingAnnotationsException, InvalidSourceMemberException {
//        MethodMapping methodMapping = ((AnnotatedElement) destMember).getAnnotation(MethodMapping.class);
//        FieldMapping fieldMapping = ((AnnotatedElement) destMember).getAnnotation(FieldMapping.class);
//        // Check if the name is annotated with @MethodMapping and @FieldMapping
//        if (methodMapping != null && fieldMapping != null) {
//            throw new MultipleMappingAnnotationsException(destClass, destMember);
//        }
//        // Retrieve the source member
//        return getSourceMember(srcClass, destClass, destMember, methodMapping, fieldMapping);
//    }
//
//    private <S, D> Member getSourceMember(Class<S> srcClass, Class<D> destClass, Member destMember, MethodMapping methodMapping, FieldMapping fieldMapping) throws InvalidSourceMemberException {
//        Member srcMember = null;
//        try {
//            if (methodMapping != null && methodMapping.source().isAssignableFrom(srcClass)) {
//                srcMember = retrieveSourceMember(srcClass, methodMapping);
//            } else if (fieldMapping != null && fieldMapping.source().isAssignableFrom(srcClass)) {
//                srcMember = retrieveSourceMember(srcClass, fieldMapping);
//            }
//        } catch (NoSuchMethodException | NoSuchFieldException e) {
//            throw new InvalidSourceMemberException(srcClass, destClass, destMember);
//        }
//        return srcMember;
//    }
//
//    private <S, D> void validateCompatibility(Class<S> srcClass, Member srcMember, Class<D> destClass, Member destMember) throws IncompatibleTypesException {
//        Class<?> srcType = getSourceType(srcMember);
//        Class<?> destType = getDestinationType(destMember);
//        if (!srcType.equals(destType)) {
//            throw new IncompatibleTypesException(srcClass, srcMember, destClass, destMember);
//        }
//    }
//
//    private Method retrieveSourceMember(Class<?> clazz, MethodMapping method) throws NoSuchMethodException {
//        // Current version supports parameter-less source methods only
//        return clazz.getDeclaredMethod(method.name());
//    }
//
//    private Field retrieveSourceMember(Class<?> clazz, FieldMapping field) throws NoSuchFieldException {
//        return clazz.getDeclaredField(field.name());
//    }
//
//    private Class<?> getSourceType(Member member) {
//        if (member instanceof Field) {
//            return ((Field) member).getType();
//        } else if (member instanceof Method) {
//            return ((Method) member).getReturnType();
//        } else {
//            // Practically this line should be unreachable
//            throw new RuntimeException("Unknown member type!");
//        }
//    }
//
//    private Class<?> getDestinationType(Member member) {
//        if (member instanceof Field) {
//            return ((Field) member).getType();
//        } else if (member instanceof Method) {
//            return ((Method) member).getParameterTypes()[0];
//        } else {
//            // Practically this line should be unreachable
//            throw new RuntimeException("Unknown member type!");
//        }
//    }
//
//}