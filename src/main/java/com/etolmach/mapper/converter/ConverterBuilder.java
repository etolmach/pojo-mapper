package com.etolmach.mapper.converter;

import com.etolmach.mapper.exceptions.CannotInstantiateConverterException;
import org.apache.commons.jxpath.util.TypeConverter;

/**
 * @author etolmach
 */
public interface ConverterBuilder {

    TypeConverter build(Class<? extends TypeConverter> converterClass) throws CannotInstantiateConverterException;

}
