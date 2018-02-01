package com.etolmach.mapper;

import com.etolmach.mapper.converter.CachedConverterByTypeProvider;
import com.etolmach.mapper.converter.ConverterByNameProvider;
import com.etolmach.mapper.converter.ConverterByTypeProvider;
import com.etolmach.mapper.exceptions.*;
import com.etolmach.mapper.objects.Source;
import com.etolmach.mapper.objects.Source2;
import com.etolmach.mapper.objects.dto.invalid.*;
import com.etolmach.mapper.objects.dto.valid.*;
import com.etolmach.mapper.objects.dto.valid.explicit.MultipleSourcesMixedMappingDto;
import org.apache.commons.jxpath.util.BasicTypeConverter;
import org.apache.commons.jxpath.util.TypeConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.etolmach.mapper.MapperUtils.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author etolmach
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultMapperBuilderTest {

    private DefaultMapperBuilder builder;

    @Mock
    private ConverterByTypeProvider converterByTypeProvider;
    @Mock
    private ConverterByNameProvider converterByNameProvider = null;
    @Mock
    private TypeConverter converter;
    @Mock
    private TypeConverter converter2;

    @Before
    public void setUp() {
        builder = spy(new DefaultMapperBuilder(converterByTypeProvider, converterByNameProvider));
    }

    @Test
    public void testDefaultConstructor() {
        DefaultMapperBuilder builder = new DefaultMapperBuilder();

        assertTrue(builder.converterByTypeProvider instanceof CachedConverterByTypeProvider);
        assertNull(builder.converterByNameProvider);
    }

    @Test
    public void buildByClasses() throws Exception {
        testBuild(
                Source.class, FieldToFieldMappingDto.class,
                list(
                        details(
                                field(Source.class, "stringField"),
                                field(FieldToFieldMappingDto.class, "testStringField"),
                                String.class
                        ),
                        details(
                                field(Source.class, "primitiveCharField"),
                                field(FieldToFieldMappingDto.class, "testPrimitiveCharField"),
                                char.class
                        ),
                        details(
                                field(Source.class, "primitiveIntField"),
                                field(FieldToFieldMappingDto.class, "testPrimitiveIntField"),
                                int.class
                        ),
                        details(
                                field(Source.class, "doubleField"),
                                field(FieldToFieldMappingDto.class, "testDoubleField"),
                                Double.class
                        ),
                        details(
                                field(Source.class, "bigDecimalField"),
                                field(FieldToFieldMappingDto.class, "testBigDecimalField"),
                                BigDecimal.class
                        )
                )
        );
    }

    @Test
    public void buildByObjects() throws Exception {
        testBuild(
                new Source(), new FieldToFieldMappingDto(),
                list(
                        details(
                                field(Source.class, "stringField"),
                                field(FieldToFieldMappingDto.class, "testStringField"),
                                String.class
                        ),
                        details(
                                field(Source.class, "primitiveCharField"),
                                field(FieldToFieldMappingDto.class, "testPrimitiveCharField"),
                                char.class
                        ),
                        details(
                                field(Source.class, "primitiveIntField"),
                                field(FieldToFieldMappingDto.class, "testPrimitiveIntField"),
                                int.class
                        ),
                        details(
                                field(Source.class, "doubleField"),
                                field(FieldToFieldMappingDto.class, "testDoubleField"),
                                Double.class
                        ),
                        details(
                                field(Source.class, "bigDecimalField"),
                                field(FieldToFieldMappingDto.class, "testBigDecimalField"),
                                BigDecimal.class
                        )
                )
        );
    }

    @Test
    public void buildAllByArrayOfTypes() throws Exception {
        when(converterByTypeProvider.provide(BasicTypeConverter.class)).thenReturn(converter);
        when(converterByNameProvider.provide("test")).thenReturn(converter2);

        builder.buildAll(MultipleSourcesMixedMappingDto.class, Source.class, Source2.class);

        assertBuilderGeneratesValidData(
                builder,
                Arrays.asList(Source.class, Source2.class),
                MultipleSourcesMixedMappingDto.class,
                list(
                        list(
                                details(
                                        field(Source.class, "stringField"),
                                        field(MultipleSourcesMixedMappingDto.class, "testStringField"),
                                        String.class
                                ),
                                details(
                                        method(Source.class, "getPrimitiveCharField"),
                                        field(MultipleSourcesMixedMappingDto.class, "testPrimitiveCharField"),
                                        char.class,
                                        converter
                                ),
                                details(
                                        field(Source.class, "primitiveIntField"),
                                        field(MultipleSourcesMixedMappingDto.class, "testPrimitiveIntField"),
                                        int.class
                                )
                        ),
                        list(
                                details(
                                        field(Source2.class, "bigDecimalField"),
                                        method(MultipleSourcesMixedMappingDto.class, "setTestBigDecimalField", BigDecimal.class),
                                        BigDecimal.class,
                                        converter2
                                ),
                                details(
                                        method(Source2.class, "getDoubleField"),
                                        method(MultipleSourcesMixedMappingDto.class, "setTestDoubleField", Double.class),
                                        Double.class
                                )

                        )
                )
        );
    }

    @Test
    public void buildAllByListOfTypes() throws Exception {
        when(converterByTypeProvider.provide(BasicTypeConverter.class)).thenReturn(converter);
        when(converterByNameProvider.provide("test")).thenReturn(converter2);

        builder.buildAll(MultipleSourcesMixedMappingDto.class, list(Source.class, Source2.class));

        verify(builder).buildAll(MultipleSourcesMixedMappingDto.class, Source.class, Source2.class);

        assertBuilderGeneratesValidData(
                builder,
                Arrays.asList(Source.class, Source2.class),
                MultipleSourcesMixedMappingDto.class,
                list(
                        list(
                                details(
                                        field(Source.class, "stringField"),
                                        field(MultipleSourcesMixedMappingDto.class, "testStringField"),
                                        String.class
                                ),
                                details(
                                        method(Source.class, "getPrimitiveCharField"),
                                        field(MultipleSourcesMixedMappingDto.class, "testPrimitiveCharField"),
                                        char.class,
                                        converter
                                ),
                                details(
                                        field(Source.class, "primitiveIntField"),
                                        field(MultipleSourcesMixedMappingDto.class, "testPrimitiveIntField"),
                                        int.class
                                )
                        ),
                        list(
                                details(
                                        field(Source2.class, "bigDecimalField"),
                                        method(MultipleSourcesMixedMappingDto.class, "setTestBigDecimalField", BigDecimal.class),
                                        BigDecimal.class,
                                        converter2
                                ),
                                details(
                                        method(Source2.class, "getDoubleField"),
                                        method(MultipleSourcesMixedMappingDto.class, "setTestDoubleField", Double.class),
                                        Double.class
                                )

                        )
                )
        );
    }

    @Test(expected = InvalidSourceMemberException.class)
    public void nonExistingSourceField() throws MapperConfigurationException {
        builder.build(Source.class, NonExistingSourceFieldDto.class);
    }

    @Test(expected = InvalidSourceMemberException.class)
    public void nonExistingSourceMethod() throws MapperConfigurationException {
        builder.build(Source.class, NonExistingSourceMethodDto.class);
    }

    @Test(expected = IncompatibleTypesException.class)
    public void incompatibleSourceFieldTypeDto() throws MapperConfigurationException {
        builder.build(Source.class, IncompatibleSourceFieldTypeDto.class);
    }

    @Test(expected = IncompatibleTypesException.class)
    public void incompatibleSourceMethodTypeDto() throws MapperConfigurationException {
        builder.build(Source.class, IncompatibleSourceMethodTypeDto.class);
    }

    @Test(expected = MultipleMappingAnnotationsException.class)
    public void multipleMappingAnnotationsOnFieldDto() throws MapperConfigurationException {
        builder.build(Source.class, MultipleMappingAnnotationsOnFieldDto.class);
    }

    @Test(expected = MultipleMappingAnnotationsException.class)
    public void multipleMappingAnnotationsOnMethodDto() throws MapperConfigurationException {
        builder.build(Source.class, MultipleMappingAnnotationsOnMethodDto.class);
    }

    @Test(expected = MultipleConvertersDefinedException.class)
    public void multipleConvertersOnFieldMapping() throws MapperConfigurationException {
        builder.build(Source.class, MultipleConvertersOnFieldMappingDto.class);
    }

    @Test(expected = MultipleConvertersDefinedException.class)
    public void multipleConvertersOnMethodMapping() throws MapperConfigurationException {
        builder.build(Source.class, MultipleConvertersOnMethodMappingDto.class);
    }

    @Test
    public void provideConverterByNameFromOnFieldMapping() throws MapperConfigurationException, ReflectiveOperationException {
        when(converterByNameProvider.provide(NamedConverterOnFieldMappingDto.CONVERTER_NAME)).thenReturn(converter);

        testBuild(
                Source.class,
                NamedConverterOnFieldMappingDto.class,
                list(
                        details(
                                Source.class.getDeclaredField("stringField"),
                                NamedConverterOnFieldMappingDto.class.getDeclaredMethod("setTestFoo", String.class),
                                String.class,
                                converter
                        )
                )
        );
    }

    @Test(expected = CannotProvideConverterException.class)
    public void provideConverterByNameFromOnMethodMapping() throws MapperConfigurationException {
        when(converterByNameProvider.provide(anyString())).thenThrow(CannotProvideConverterException.class);

        builder.build(Source.class, NamedConverterOnMethodMappingDto.class);
    }

    @Test(expected = CannotProvideConverterException.class)
    public void provideConverterByTypeFromOnFieldMapping() throws MapperConfigurationException {
        when(converterByTypeProvider.provide(any())).thenThrow(CannotProvideConverterException.class);

        builder.build(Source.class, TypedConverterOnFieldMappingDto.class);
    }

    @Test(expected = CannotProvideConverterException.class)
    public void provideConverterByTypeFromOnMethodMapping() throws MapperConfigurationException {
        when(converterByTypeProvider.provide(any())).thenThrow(CannotProvideConverterException.class);

        builder.build(Source.class, TypedConverterOnMethodMappingDto.class);
    }

    @Test(expected = CannotProvideConverterException.class)
    public void cannotProvideConverterByNameFromOnFieldMapping() throws MapperConfigurationException {
        when(converterByNameProvider.provide(anyString())).thenThrow(CannotProvideConverterException.class);

        builder.build(Source.class, NamedConverterOnFieldMappingDto.class);
    }

    @Test(expected = CannotProvideConverterException.class)
    public void cannotProvideConverterByNameFromOnMethodMapping() throws MapperConfigurationException {
        when(converterByNameProvider.provide(anyString())).thenThrow(CannotProvideConverterException.class);

        builder.build(Source.class, NamedConverterOnMethodMappingDto.class);
    }

    @Test(expected = CannotProvideConverterException.class)
    public void cannotProvideConverterByTypeFromOnFieldMapping() throws MapperConfigurationException {
        when(converterByTypeProvider.provide(any())).thenThrow(CannotProvideConverterException.class);

        builder.build(Source.class, TypedConverterOnFieldMappingDto.class);
    }

    @Test(expected = CannotProvideConverterException.class)
    public void cannotProvideConverterByTypeFromOnMethodMapping() throws MapperConfigurationException {
        when(converterByTypeProvider.provide(any())).thenThrow(CannotProvideConverterException.class);

        builder.build(Source.class, TypedConverterOnMethodMappingDto.class);
    }

    @Test
    public void unsupportedSourceMemberTypeConverterByTypeFromOnMethodMapping() throws MapperConfigurationException, NoSuchMethodException {
        when(converterByTypeProvider.provide(any())).thenThrow(CannotProvideConverterException.class);

        // TODO : expected RuntimeException

        testBuild(
                Source.class,
                NamedConverterOnFieldMappingDto.class,
                list(
                        details(
                                Source.class.getConstructor(),
                                NamedConverterOnFieldMappingDto.class.getDeclaredMethod("setTestFoo", String.class),
                                String.class,
                                converter
                        )
                )
        );
    }

    // Utility methods
    private <S, D> void testBuild(S srcObject, D destObject, List<MappingDetails> expectedMappingDetails) throws MapperConfigurationException {
        builder.build(srcObject, destObject);
        assertBuilderGeneratesValidData(builder, Arrays.asList(srcObject.getClass()), destObject.getClass(), Arrays.asList(expectedMappingDetails));
    }

    private void testBuild(Class<?> srcClass, Class<?> destClass, List<MappingDetails> expectedMappingDetails) throws MapperConfigurationException {
        testBuild(builder, srcClass, destClass, expectedMappingDetails);
    }

    private void testBuild(DefaultMapperBuilder builder, Class<?> srcClass, Class<?> destClass, List<MappingDetails> expectedMappingDetails) throws MapperConfigurationException {
        builder.build(srcClass, destClass);
        assertBuilderGeneratesValidData(builder, Arrays.asList(srcClass), destClass, Arrays.asList(expectedMappingDetails));
    }

    private void assertBuilderGeneratesValidData(DefaultMapperBuilder builder, List<Class<?>> expectedSrcClasses, Class<?> expectedDestClass, List<List<MappingDetails>> expectedMappingDetailsList) {
        // Create the captors
        ArgumentCaptor<Class<?>> srcClassCaptor = ArgumentCaptor.forClass(Class.class);
        ArgumentCaptor<Class<?>> destClassCaptor = ArgumentCaptor.forClass(Class.class);
        ArgumentCaptor<List<MappingDetails>> detailsCaptor = ArgumentCaptor.forClass(List.class);

        // Capture the mapper builder data
        verify(builder, times(expectedMappingDetailsList.size())).buildFor(
                srcClassCaptor.capture(),
                destClassCaptor.capture(),
                detailsCaptor.capture()
        );

        // Fetch captured builder data
        List<Class<?>> fetchedSrcClasses = srcClassCaptor.getAllValues();
        List<Class<?>> fetchedDestClasses = destClassCaptor.getAllValues();
        List<List<MappingDetails>> fetchedMappingDetailsList = detailsCaptor.getAllValues();

        // Verify each call of buildFor() method
        for (int i = 0; i < expectedMappingDetailsList.size(); i++) {
            assertEquals(expectedSrcClasses.get(i), fetchedSrcClasses.get(i));
            assertEquals(expectedDestClass, fetchedDestClasses.get(i));

            List<MappingDetails> fetchedMappingDetails = fetchedMappingDetailsList.get(i);
            List<MappingDetails> expectedMappingDetails = expectedMappingDetailsList.get(i);
            assertEquals(expectedMappingDetails.size(), fetchedMappingDetails.size());
            assertTrue(expectedMappingDetails.containsAll(fetchedMappingDetails));
        }
    }
}