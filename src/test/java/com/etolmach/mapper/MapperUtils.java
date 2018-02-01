package com.etolmach.mapper;

import org.apache.commons.jxpath.util.TypeConverter;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author etolmach
 */
public class MapperUtils {

    public static Field field(Class<?> clazz, String name) {
        return field(clazz, name, true);
    }

    public static Field field(Class<?> clazz, String name, boolean allowAccessibility) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(allowAccessibility);
            return field;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static Method method(Class<?> clazz, String name, Class<?>... parameterTypes) {
        return method(clazz, name, true, parameterTypes);
    }

    public static Method method(Class<?> clazz, String name, boolean allowAccessibility, Class<?>... parameterTypes) {
        try {
            Method method = clazz.getDeclaredMethod(name, parameterTypes);
            method.setAccessible(allowAccessibility);
            return method;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static <S, D> BaseMappingDetails<S, D> details(Member srcMember, Member destMember, Class<?> destType) {
        return details(srcMember, destMember, destType, null);
    }

    public static <S, D> BaseMappingDetails<S, D> details(Member srcMember, Member destMember, Class<?> destType, TypeConverter converter) {
        return new BaseMappingDetails<>(srcMember, destMember, destType, converter);
    }

    public static <E> List<E> list(E... elements) {
        return Arrays.asList(elements);
    }

}
