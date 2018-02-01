package com.etolmach.mapper.exceptions;

import java.lang.reflect.Member;

/**
 * @author etolmach
 */
public class MultipleConvertersDefinedException extends MapperConfigurationException {

    public static final String MESSAGE = "Multiple converters defined for member ''{0}'' of class ''{1}''.";

    public MultipleConvertersDefinedException(Class<?> clazz, Member destMember) {
        super(MESSAGE, destMember.getName(), clazz.getName());
    }
}
