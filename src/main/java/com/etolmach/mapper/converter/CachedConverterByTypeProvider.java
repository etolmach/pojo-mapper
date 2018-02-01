package com.etolmach.mapper.converter;

import com.etolmach.mapper.exceptions.CannotInstantiateConverterException;
import com.etolmach.mapper.exceptions.CannotProvideConverterException;
import lombok.NonNull;
import org.apache.commons.jxpath.util.TypeConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author etolmach
 */
public class CachedConverterByTypeProvider implements ConverterByTypeProvider {

    private final Map<Class<? extends TypeConverter>, TypeConverter> cachedConverters;

    protected final ConverterBuilder builder;

    public CachedConverterByTypeProvider() {
        this(new ReflectiveConverterBuilder());
    }

    public CachedConverterByTypeProvider(@NonNull ConverterBuilder builder) {
        this.builder = builder;
        this.cachedConverters = new HashMap<>();
    }

    @Override
    public TypeConverter provide(Class<? extends TypeConverter> converterClass) throws CannotProvideConverterException {
        TypeConverter converter = cachedConverters.get(converterClass);
        if (converter == null) {
            try {
                converter = builder.build(converterClass);
            } catch (CannotInstantiateConverterException e) {
                throw new CannotProvideConverterException(converterClass.getName(), e);
            }
            cachedConverters.put(converterClass, converter);
        }
        return converter;
    }

}
