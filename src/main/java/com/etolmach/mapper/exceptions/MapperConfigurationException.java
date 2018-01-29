package com.etolmach.mapper.exceptions;

/**
 * @author etolmach
 */
public abstract class MapperConfigurationException extends MapperException {

    public MapperConfigurationException(String message, Object... messageParameters) {
        super(message, messageParameters);
    }

    public MapperConfigurationException(Throwable cause, String message, Object... messageParameters) {
        super(cause, message, messageParameters);
    }

}
