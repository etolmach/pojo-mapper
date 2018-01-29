package com.etolmach.mapper.exceptions;

/**
 * @author etolmach
 */
public class CannotInstantiateConverterException extends MapperExecutionException {

    private static final String MESSAGE = "Cannot instantiate an object of converter class ''{0}''.";

    public CannotInstantiateConverterException(Class<?> converterClass, Throwable cause) {
        super(cause, MESSAGE, converterClass.getName());
    }
}
