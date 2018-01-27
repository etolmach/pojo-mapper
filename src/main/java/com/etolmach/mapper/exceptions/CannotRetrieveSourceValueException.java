package com.etolmach.mapper.exceptions;

import java.lang.reflect.Member;

/**
 * @author etolmach
 */
public class CannotRetrieveSourceValueException extends MapperExecutionException {

    private static final String MESSAGE = "Cannot retrieve source value from member ''{0}'' of class ''{1}''.";

    public CannotRetrieveSourceValueException(Class<?> srcClass, Member srcMember, Throwable cause) {
        super(cause, MESSAGE, srcMember.getName(), srcClass.getName());
    }

}
