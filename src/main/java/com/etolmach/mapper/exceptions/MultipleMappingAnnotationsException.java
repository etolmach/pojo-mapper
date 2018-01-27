package com.etolmach.mapper.exceptions;

import java.lang.reflect.Member;

/**
 * @author etolmach
 */
public class MultipleMappingAnnotationsException extends MapperConfigurationException {

    public static final String MESSAGE = "Multiple mapping annotations found for member ''{0}'' of class ''{1}''.";

    public MultipleMappingAnnotationsException(Class<?> clazz, Member destMember) {
        super(MESSAGE, destMember.getName(), clazz.getName());
    }
}
