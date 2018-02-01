package com.etolmach.mapper.converter;

import org.apache.commons.jxpath.util.TypeConverter;

/**
 * @author etolmach
 */
public class NonAssignableConverter implements TypeConverter {
    @Override
    public boolean canConvert(Object o, Class aClass) {
        return false;
    }

    @Override
    public Object convert(Object o, Class aClass) {
        return null;
    }
}
