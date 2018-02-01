package com.etolmach.mapper.converter.valid;

import org.apache.commons.jxpath.util.TypeConverter;

/**
 * @author etolmach
 */
public class ConverterWithPrivateConstructor implements TypeConverter {

    @Override
    public boolean canConvert(Object o, Class aClass) {
        return false;
    }

    @Override
    public Object convert(Object o, Class aClass) {
        return null;
    }

    private ConverterWithPrivateConstructor() {
        
    }

}
