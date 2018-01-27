package com.etolmach.mapper.exceptions;

import java.lang.reflect.Member;

/**
 * @author etolmach
 */
public class IncompatibleTypesException extends MapperConfigurationException {

    private static final String MESSAGE = "Member ''{0}'' of class ''{1}'' is not compatible to member ''{2}'' of class ''{3}''";

    public IncompatibleTypesException(Class<?> srcClass, Member srcMember, Class<?> destClass, Member destMember) {
        super(MESSAGE, srcMember.getName(), srcClass, destMember.getName(), destClass);
    }

}
