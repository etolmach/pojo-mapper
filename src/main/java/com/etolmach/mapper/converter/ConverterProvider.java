package com.etolmach.mapper.converter;

import com.etolmach.mapper.exceptions.CannotProvideConverterException;
import org.apache.commons.jxpath.util.TypeConverter;

/**
 * @author etolmach
 */
public interface ConverterProvider<T> {

    TypeConverter provide(T id) throws CannotProvideConverterException;

}
