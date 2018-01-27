package com.etolmach.mapper.exceptions;

import java.lang.reflect.Member;

/**
 * @author etolmach
 */
public class CannotInjectValueException extends MapperExecutionException {

    private static final String MESSAGE = "Cannot inject value to member ''{0}'' of class ''{1}''.";

    public CannotInjectValueException(Class<?> srcClass, Member srcMember, Throwable cause) {
        super(cause, MESSAGE, srcMember.getName(), srcClass.getName());
    }

}
