package com.etolmach.mapper.exceptions;

import java.text.MessageFormat;

/**
 * @author etolmach
 */
public abstract class MapperException extends Exception {

    public MapperException(String message, Object... messageParameters) {
        super(MessageFormat.format(message, messageParameters));
    }

    public MapperException(Throwable cause, String message, Object... messageParameters) {
        super(MessageFormat.format(message, messageParameters), cause);
    }
}
