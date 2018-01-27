package com.etolmach.mapper.exceptions;

/**
 * @author etolmach
 */
public class CannotInstantiateDestinationObject extends MapperExecutionException {

    private static final String MESSAGE = "Cannot instantiate an object of destination class ''{0}''.";

    public CannotInstantiateDestinationObject(Class<?> destClass, Throwable cause) {
        super(cause, MESSAGE, destClass.getName());
    }
}
