package com.etolmach.mapper.exceptions;

import java.text.MessageFormat;

/**
 * @author etolmach
 */
public class ConverterException extends Exception {

    public static final String MESSAGE = "Cannot format {0} using {2}. Caused by : {3}";

    public ConverterException(Class<?> srcClass, Class<?> converterClass, Throwable cause) {
        super(MessageFormat.format(MESSAGE, srcClass.getName(),converterClass.getName(), cause.getMessage()), cause);
    }

}
