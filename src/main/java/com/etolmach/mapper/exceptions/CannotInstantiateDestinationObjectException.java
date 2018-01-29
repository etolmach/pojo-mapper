package com.etolmach.mapper.exceptions;

/**
 * @author etolmach
 */
public class CannotInstantiateDestinationObjectException extends MapperExecutionException {

    private static final String MESSAGE = "Cannot instantiate an object of destination class ''{0}''.";

    public CannotInstantiateDestinationObjectException(Class<?> destClass, Throwable cause) {
        super(cause, MESSAGE, destClass.getName());
    }
}
