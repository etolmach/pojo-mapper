package com.etolmach.mapper.converter;

import com.etolmach.mapper.exceptions.CannotInstantiateConverterException;
import org.apache.commons.jxpath.util.TypeConverter;

/**
 * @author etolmach
 */
public class ReflectiveConverterBuilder implements ConverterBuilder {

    @Override
    public TypeConverter build(Class<? extends TypeConverter> converterClass) throws CannotInstantiateConverterException {
        try {
            return converterClass.getConstructor().newInstance();
        } catch (ReflectiveOperationException exception) {
            throw new CannotInstantiateConverterException(converterClass, exception);
        }
    }

}
