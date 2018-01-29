package com.etolmach.mapper.exceptions;

/**
 * @author etolmach
 */
public class CannotProvideConverterException extends MapperConfigurationException {

    private static final String MESSAGE = "Cannot provide a converter by key ''{0}''.";

    public CannotProvideConverterException(String key, Throwable cause) {
        super(cause, MESSAGE, key);
    }
}
