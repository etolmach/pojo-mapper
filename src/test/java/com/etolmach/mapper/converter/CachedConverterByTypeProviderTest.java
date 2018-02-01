package com.etolmach.mapper.converter;

import com.etolmach.mapper.exceptions.CannotInstantiateConverterException;
import com.etolmach.mapper.exceptions.CannotProvideConverterException;
import org.apache.commons.jxpath.util.TypeConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * @author etolmach
 */
@RunWith(MockitoJUnitRunner.class)
public class CachedConverterByTypeProviderTest {

    private CachedConverterByTypeProvider provider;

    private static final Class<? extends TypeConverter> CONVERTER_TYPE = ValidStringToIntegerConverter.class;

    @Mock
    private ConverterBuilder builder;
    @Mock
    private TypeConverter converter;

    @Before
    public void setUp() throws Exception {
        provider = new CachedConverterByTypeProvider(builder);
    }

    @Test
    public void defaultConstructor() {
        CachedConverterByTypeProvider provider = new CachedConverterByTypeProvider();

        assertTrue(provider.builder instanceof ReflectiveConverterBuilder);
    }

    @Test
    public void provideNewConverter() throws Exception {
        when(builder.build(CONVERTER_TYPE)).thenReturn(converter);

        assertEquals(converter, provider.provide(CONVERTER_TYPE));
    }

    @Test(expected = CannotProvideConverterException.class)
    public void cannotProvideNewConverter() throws Exception {
        doThrow(CannotInstantiateConverterException.class).when(builder).build(CONVERTER_TYPE);

        provider.provide(CONVERTER_TYPE);
    }

    @Test
    public void provideCachedConverter() throws Exception {
        when(builder.build(CONVERTER_TYPE)).thenReturn(converter);

        assertEquals(converter, provider.provide(CONVERTER_TYPE));
        assertEquals(converter, provider.provide(CONVERTER_TYPE));

        verify(builder, times(1)).build(CONVERTER_TYPE);
    }
}