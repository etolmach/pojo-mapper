package com.etolmach.mapper.exceptions;

import org.apache.commons.jxpath.util.TypeConverter;

/**
 * @author etolmach
 */
public class CannotConvertObjectException extends MapperExecutionException {

    private static final String MESSAGE = "Cannot convert {0} to type ''{1}'' using converter {2}.";

    public CannotConvertObjectException(Object object, Class<?> type, TypeConverter converter) {
        super(MESSAGE, object, type, converter);
    }

}
