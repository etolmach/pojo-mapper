package com.etolmach.mapper.exceptions;

import java.lang.reflect.Member;

/**
 * @author etolmach
 */
public class InvalidSourceMemberException extends MapperConfigurationException {

    private static final String MESSAGE = "Member ''{0}'' of class ''{1}'' points to non-existing member of class ''{2}''.";

    public InvalidSourceMemberException(Class<?> srcClass, Class<?> destClass, Member destMember) {
        super(MESSAGE, destMember.getName(), destClass.getName(), srcClass.getName());
    }

}
