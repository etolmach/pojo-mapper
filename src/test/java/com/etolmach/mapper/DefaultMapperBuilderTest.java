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

    public static final String TEST_STRING_FIELD = "testStringField";
    public static final String TEST_PRIMITIVE_CHAR_FIELD = "testPrimitiveCharField";
    public static final String TEST_PRIMITIVE_INT_FIELD = "testPrimitiveIntField";
    public static final String TEST_DOUBLE_FIELD = "testDoubleField";
    public static final String TEST_BIG_DECIMAL_FIELD = "testBigDecimalField";

    public static final String SET_TEST_STRING_FIELD = "setTestStringField";
    public static final String SET_TEST_DOUBLE_FIELD = "setTestDoubleField";
    public static final String SET_TEST_BIG_DECIMAL_FIELD = "setTestBigDecimalField";

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
                                Source.STRING_FIELD,
                                field(FieldToFieldMappingDto.class, TEST_STRING_FIELD),
                                String.class
                        ),
                        details(
                                Source.PRIMITIVE_CHAR_FIELD,
                                field(FieldToFieldMappingDto.class, TEST_PRIMITIVE_CHAR_FIELD),
                                char.class
                        ),
                        details(
                                Source.PRIMITIVE_INT_FIELD,
                                field(FieldToFieldMappingDto.class, TEST_PRIMITIVE_INT_FIELD),
                                int.class
                        ),
                        details(
                                Source.DOUBLE_FIELD,
                                field(FieldToFieldMappingDto.class, TEST_DOUBLE_FIELD),
                                Double.class
                        ),
                        details(
                                Source.BIG_DECIMAL_FIELD,
                                field(FieldToFieldMappingDto.class, TEST_BIG_DECIMAL_FIELD),
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
                                Source.STRING_FIELD,
                                field(FieldToFieldMappingDto.class, TEST_STRING_FIELD),
                                String.class
                        ),
                        details(
                                Source.PRIMITIVE_CHAR_FIELD,
                                field(FieldToFieldMappingDto.class, TEST_PRIMITIVE_CHAR_FIELD),
                                char.class
                        ),
                        details(
                                Source.PRIMITIVE_INT_FIELD,
                                field(FieldToFieldMappingDto.class, TEST_PRIMITIVE_INT_FIELD),
                                int.class
                        ),
                        details(
                                Source.DOUBLE_FIELD,
                                field(FieldToFieldMappingDto.class, TEST_DOUBLE_FIELD),
                                Double.class
                        ),
                        details(
                                Source.BIG_DECIMAL_FIELD,
                                field(FieldToFieldMappingDto.class, TEST_BIG_DECIMAL_FIELD),
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
                                        Source.STRING_FIELD,
                                        field(MultipleSourcesMixedMappingDto.class, TEST_STRING_FIELD),
                                        String.class
                                ),
                                details(
                                        Source.GET_PRIMITIVE_CHAR_METHOD,
                                        field(MultipleSourcesMixedMappingDto.class, TEST_PRIMITIVE_CHAR_FIELD),
                                        char.class,
                                        converter
                                ),
                                details(
                                        Source.PRIMITIVE_INT_FIELD,
                                        field(MultipleSourcesMixedMappingDto.class, TEST_PRIMITIVE_INT_FIELD),
                                        int.class
                                )
                        ),
                        list(
                                details(
                                        Source2.GET_DOUBLE_METHOD,
                                        method(MultipleSourcesMixedMappingDto.class, SET_TEST_DOUBLE_FIELD, Double.class),
                                        Double.class
                                ),
                                details(
                                        Source2.BIG_DECIMAL_FIELD,
                                        method(MultipleSourcesMixedMappingDto.class, SET_TEST_BIG_DECIMAL_FIELD, BigDecimal.class),
                                        BigDecimal.class,
                                        converter2
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
                                        Source.STRING_FIELD,
                                        field(MultipleSourcesMixedMappingDto.class, TEST_STRING_FIELD),
                                        String.class
                                ),
                                details(
                                        Source.GET_PRIMITIVE_CHAR_METHOD,
                                        field(MultipleSourcesMixedMappingDto.class, TEST_PRIMITIVE_CHAR_FIELD),
                                        char.class,
                                        converter
                                ),
                                details(
                                        Source.PRIMITIVE_INT_FIELD,
                                        field(MultipleSourcesMixedMappingDto.class, TEST_PRIMITIVE_INT_FIELD),
                                        int.class
                                )
                        ),
                        list(
                                details(
                                        Source2.GET_DOUBLE_METHOD,
                                        method(MultipleSourcesMixedMappingDto.class, SET_TEST_DOUBLE_FIELD, Double.class),
                                        Double.class
                                ),
                                details(
                                        Source2.BIG_DECIMAL_FIELD,
                                        method(MultipleSourcesMixedMappingDto.class, SET_TEST_BIG_DECIMAL_FIELD, BigDecimal.class),
                                        BigDecimal.class,
                                        converter2
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
                                Source.STRING_FIELD,
                                NamedConverterOnFieldMappingDto.class.getDeclaredMethod(SET_TEST_STRING_FIELD, String.class),
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