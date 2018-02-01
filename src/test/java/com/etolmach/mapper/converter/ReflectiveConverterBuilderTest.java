package com.etolmach.mapper.converter;

import com.etolmach.mapper.converter.valid.ConverterWithPrivateConstructor;
import com.etolmach.mapper.converter.valid.ConverterWithoutDefaultConstructor;
import com.etolmach.mapper.exceptions.CannotInstantiateConverterException;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author etolmach
 */
public class ReflectiveConverterBuilderTest {

    private ReflectiveConverterBuilder builder = new ReflectiveConverterBuilder();

    @Test
    public void build() throws CannotInstantiateConverterException {
        assertNotNull(builder.build(ValidStringToIntegerConverter.class));
    }

    @Test(expected = CannotInstantiateConverterException.class)
    public void privateConstructor() throws CannotInstantiateConverterException {
        builder.build(ConverterWithPrivateConstructor.class);
    }

    @Test(expected = CannotInstantiateConverterException.class)
    public void missingDefaultConstructor() throws CannotInstantiateConverterException {
        builder.build(ConverterWithoutDefaultConstructor.class);
    }
}