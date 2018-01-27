package com.etolmach.mapper.exceptions;

/**
 * @author etolmach
 */
public abstract class MapperExecutionException extends MapperException {

    public MapperExecutionException(Throwable cause, String message, Object... messageParameters) {
        super(cause, message, messageParameters);
    }

}
