package com.etolmach.mapper.converter.valid;

import org.apache.commons.jxpath.util.TypeConverter;

/**
 * @author etolmach
 */
public class ConverterWithoutDefaultConstructor implements TypeConverter {

    @Override
    public boolean canConvert(Object o, Class aClass) {
        return false;
    }

    @Override
    public Object convert(Object o, Class aClass) {
        return null;
    }

    // No default constructor defined

    private ConverterWithoutDefaultConstructor(String str) {
        // DO NOTHING
    }

}
