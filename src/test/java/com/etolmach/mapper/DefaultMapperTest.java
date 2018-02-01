package com.etolmach.mapper;

import com.etolmach.mapper.exceptions.*;
import com.etolmach.mapper.objects.Destination;
import com.etolmach.mapper.objects.Source;
import com.etolmach.mapper.objects.Source2;
import com.etolmach.mapper.objects.dto.invalid.PrivateConstructorDto;
import org.apache.commons.jxpath.util.TypeConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;

import static com.etolmach.mapper.MapperUtils.details;
import static com.etolmach.mapper.MapperUtils.list;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author etolmach
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultMapperTest {

    private static final String STRING = "String field";
    private static final int PRIMITIVE_INT = 123456;
    private static final char PRIMITIVE_CHAR = 'T';
    private static final Double DOUBLE = 123456.789;
    private static final BigDecimal BIG_DECIMAL = new BigDecimal("123456.78901010101010101001");

    private static final String STRING_2 = "String 2";
    private static final int PRIMITIVE_INT_2 = 6343456;
    private static final char PRIMITIVE_CHAR_2 = 'x';
    private static final Double DOUBLE_2 = 123543.087134;
    private static final BigDecimal BIG_DECIMAL_2 = new BigDecimal("174771210.9823894272");

    private final Source source = new Source(STRING, PRIMITIVE_CHAR, PRIMITIVE_INT, DOUBLE, BIG_DECIMAL, STRING_2);
    private final Source2 source2 = new Source2(STRING_2, PRIMITIVE_CHAR_2, PRIMITIVE_INT_2, DOUBLE_2, BIG_DECIMAL_2);

    @Mock
    private TypeConverter converter;

    @Test
    public void validFieldToFieldMapping() throws Exception {
        testMapper(
                source, Source.class, Destination.class,
                list(
                        details(
                                Source.STRING_FIELD,
                                Destination.STRING_FIELD,
                                String.class
                        ),
                        details(
                                Source.PRIMITIVE_CHAR_FIELD,
                                Destination.PRIMITIVE_CHAR_FIELD,
                                String.class
                        ),
                        details(
                                Source.PRIMITIVE_INT_FIELD,
                                Destination.PRIMITIVE_INT_FIELD,
                                String.class
                        ),
                        details(
                                Source.DOUBLE_FIELD,
                                Destination.DOUBLE_FIELD,
                                String.class
                        ),
                        details(
                                Source.BIG_DECIMAL_FIELD,
                                Destination.BIG_DECIMAL_FIELD,
                                String.class
                        )
                )
        );
    }


    @Test
    public void validMethodToFieldMapping() throws Exception {
        testMapper(
                list(
                        details(
                                Source.GET_STRING_METHOD,
                                Destination.STRING_FIELD,
                                String.class
                        ),
                        details(
                                Source.GET_PRIMITIVE_CHAR_METHOD,
                                Destination.PRIMITIVE_CHAR_FIELD,
                                String.class
                        ),
                        details(
                                Source.GET_PRIMITIVE_INT_METHOD,
                                Destination.PRIMITIVE_INT_FIELD,
                                String.class
                        ),
                        details(
                                Source.GET_DOUBLE_METHOD,
                                Destination.DOUBLE_FIELD,
                                String.class
                        ),
                        details(
                                Source.GET_BIG_DECIMAL_METHOD,
                                Destination.BIG_DECIMAL_FIELD,
                                String.class
                        )
                )

        );
    }

    @Test
    public void validFieldToMethodMapping() throws Exception {
        testMapper(
                list(
                        details(
                                Source.STRING_FIELD,
                                Destination.SET_STRING_METHOD,
                                String.class
                        ),
                        details(
                                Source.PRIMITIVE_CHAR_FIELD,
                                Destination.SET_PRIMITIVE_CHAR_METHOD,
                                String.class
                        ),
                        details(
                                Source.PRIMITIVE_INT_FIELD,
                                Destination.SET_PRIMITIVE_INT_METHOD,
                                String.class
                        ),
                        details(
                                Source.DOUBLE_FIELD,
                                Destination.SET_DOUBLE_METHOD,
                                String.class
                        ),
                        details(
                                Source.BIG_DECIMAL_FIELD,
                                Destination.SET_BIG_DECIMAL_METHOD,
                                String.class
                        )
                )
        );
    }

    @Test
    public void validMethodToMethodMapping() throws Exception {
        testMapper(
                list(
                        details(
                                Source.GET_STRING_METHOD,
                                Destination.SET_STRING_METHOD,
                                String.class
                        ),
                        details(
                                Source.GET_PRIMITIVE_CHAR_METHOD,
                                Destination.SET_PRIMITIVE_CHAR_METHOD,
                                String.class
                        ),
                        details(
                                Source.GET_PRIMITIVE_INT_METHOD,
                                Destination.SET_PRIMITIVE_INT_METHOD,
                                String.class
                        ),
                        details(
                                Source.GET_DOUBLE_METHOD,
                                Destination.SET_DOUBLE_METHOD,
                                String.class
                        ),
                        details(
                                Source.GET_BIG_DECIMAL_METHOD,
                                Destination.SET_BIG_DECIMAL_METHOD,
                                String.class
                        )
                )
        );
    }

    @Test
    public void mapToExistingObject() throws Exception {
        testMapper(
                new Destination(),
                list(
                        details(
                                Source.STRING_FIELD,
                                Destination.SET_STRING_METHOD,
                                String.class
                        ),
                        details(
                                Source.PRIMITIVE_CHAR_FIELD,
                                Destination.SET_PRIMITIVE_CHAR_METHOD,
                                String.class
                        ),
                        details(
                                Source.PRIMITIVE_INT_FIELD,
                                Destination.SET_PRIMITIVE_INT_METHOD,
                                String.class
                        ),
                        details(
                                Source.DOUBLE_FIELD,
                                Destination.SET_DOUBLE_METHOD,
                                String.class
                        ),
                        details(
                                Source.BIG_DECIMAL_FIELD,
                                Destination.SET_BIG_DECIMAL_METHOD,
                                String.class
                        )
                )
        );
    }

    @Test(expected = CannotInstantiateDestinationObjectException.class)
    public void cannotInstantiateDestinationObject() throws MapperException {
        mapper(Source.class, PrivateConstructorDto.class, list()).map(source);
    }

    @Test(expected = CannotRetrieveSourceValueException.class)
    public void cannotRetrieveSourceValueFromField() throws MapperException {
        mapper(
                Source.class, Destination.class,
                list(
                        details(
                                Source.INACCESSIBLE_FIELD,
                                Destination.STRING_FIELD,
                                String.class
                        )
                )
        ).map(source);
    }

    @Test(expected = CannotRetrieveSourceValueException.class)
    public void cannotRetrieveSourceValueFromMethod() throws MapperException, NoSuchFieldException, NoSuchMethodException {
        mapper(
                Source.class, Destination.class,
                list(
                        details(
                                Source.INACCESSIBLE_METHOD,
                                Destination.STRING_FIELD,
                                String.class
                        )
                )
        ).map(source);
    }

    @Test(expected = CannotInjectValueException.class)
    public void cannotInjectValueToField() throws MapperException {
        mapper(
                Source.class, Destination.class,
                list(
                        details(
                                Source.STRING_FIELD,
                                Destination.INACCESSIBLE_FIELD,
                                String.class
                        )
                )
        ).map(source);
    }

    @Test(expected = CannotInjectValueException.class)
    public void cannotInjectValueToMethod() throws MapperException, NoSuchMethodException {
        mapper(
                Source.class, Destination.class,
                list(
                        details(
                                Source.STRING_FIELD,
                                Destination.INACCESSIBLE_METHOD,
                                String.class
                        )
                )
        ).map(source);
    }

    @Test
    public void multipleSourcesMappingOnFieldDto() throws MapperException {
        testMapper(
                source, Source.class, Destination.class,
                list(
                        details(
                                Source.STRING_FIELD,
                                Destination.SET_STRING_METHOD,
                                String.class
                        ),
                        details(
                                Source.PRIMITIVE_CHAR_FIELD,
                                Destination.SET_PRIMITIVE_CHAR_METHOD,
                                String.class
                        ),
                        details(
                                Source.PRIMITIVE_INT_FIELD,
                                Destination.SET_PRIMITIVE_INT_METHOD,
                                String.class
                        )
                )
        );
        testMapper(
                source2, Source2.class, Destination.class,
                list(
                        details(
                                Source2.DOUBLE_FIELD,
                                Destination.SET_DOUBLE_METHOD,
                                String.class
                        ),
                        details(
                                Source2.GET_BIG_DECIMAL_METHOD,
                                Destination.BIG_DECIMAL_FIELD,
                                String.class
                        )
                )
        );
    }


    @Test
    public void validFieldToFieldMappingWithConverter() throws Exception {
        when(converter.canConvert(STRING, BigDecimal.class)).thenReturn(true);
        when(converter.convert(STRING, BigDecimal.class)).thenReturn(BIG_DECIMAL);

        testMapper(
                source, Source.class, Destination.class,
                list(
                        details(
                                Source.STRING_FIELD,
                                Destination.BIG_DECIMAL_FIELD,
                                BigDecimal.class,
                                converter
                        )
                )
        );
    }

    @Test
    public void validFieldToMethodMappingWithConverter() throws Exception {
        when(converter.canConvert(STRING, BigDecimal.class)).thenReturn(true);
        when(converter.convert(STRING, BigDecimal.class)).thenReturn(BIG_DECIMAL);

        testMapper(
                source, Source.class, Destination.class,
                list(
                        details(
                                Source.STRING_FIELD,
                                Destination.SET_BIG_DECIMAL_METHOD,
                                BigDecimal.class,
                                converter
                        )
                )
        );
    }

    @Test
    public void validMethodToFieldMappingWithConverter() throws Exception {
        when(converter.canConvert(STRING, BigDecimal.class)).thenReturn(true);
        when(converter.convert(STRING, BigDecimal.class)).thenReturn(BIG_DECIMAL);

        testMapper(
                source, Source.class, Destination.class,
                list(
                        details(
                                Source.GET_STRING_METHOD,
                                Destination.BIG_DECIMAL_FIELD,
                                BigDecimal.class,
                                converter
                        )
                )
        );
    }

    @Test
    public void validMethodToMethodMappingWithConverter() throws Exception {
        when(converter.canConvert(STRING, BigDecimal.class)).thenReturn(true);
        when(converter.convert(STRING, BigDecimal.class)).thenReturn(BIG_DECIMAL);

        testMapper(
                source, Source.class, Destination.class,
                list(
                        details(
                                Source.GET_STRING_METHOD,
                                Destination.SET_BIG_DECIMAL_METHOD,
                                BigDecimal.class,
                                converter
                        )
                )
        );
    }

    @Test(expected = CannotConvertObjectException.class)
    public void invalidMethodToFieldMappingWithConverter() throws Exception {
        when(converter.canConvert(STRING, BigDecimal.class)).thenReturn(false);

        testMapper(
                source, Source.class, Destination.class,
                list(
                        details(
                                Source.GET_STRING_METHOD,
                                Destination.DOUBLE_FIELD,
                                BigDecimal.class,
                                converter
                        )
                )
        );
    }

    @Test(expected = CannotConvertObjectException.class)
    public void invalidMethodToMethodMappingWithConverter() throws Exception {
        // converter mock cannot convert a String to a BigDecimal
        testMapper(
                source, Source.class, Destination.class,
                list(
                        details(
                                Source.GET_STRING_METHOD,
                                Destination.SET_PRIMITIVE_INT_METHOD,
                                int.class,
                                converter
                        )
                )
        );
    }

    // Utility methods
    private <S, D> DefaultMapper<S, D> mapper(Class<S> srcClass, Class<D> destClass, List<MappingDetails> mappingDetailsList) {
        return new DefaultMapper<>(srcClass, destClass, mappingDetailsList);
    }

    private <S, D> void testMapper(S srcObject, Class<S> srcClass, Class<D> destClass, List<MappingDetails> mappingDetailsList) throws MapperException {
        DefaultMapper<S, D> mapper = mapper(srcClass, destClass, mappingDetailsList);

        mapper.map(srcObject);
    }

    private <D> void testMapper(Class<D> destinationClass, List<MappingDetails> mappingDetailsList) throws MapperException {
        testMapper(source, Source.class, destinationClass, mappingDetailsList);
    }

    private void testMapper(Destination destination, List<MappingDetails> mappingDetailsList) throws MapperException {
        testMapper(source, Source.class, destination.getClass(), mappingDetailsList);
    }

    private void testMapper(List<MappingDetails> mappingDetailsList) throws MapperException {
        Mapper<Source, Destination> mapper = mapper(Source.class, Destination.class, mappingDetailsList);

        Destination destination = mapper.map(source);

        assertMappingIsCorrect(source, destination);
    }

    private void assertMappingIsCorrect(Source source, Destination destination) {
        assertEquals(source.getStringField(), destination.getDestStringField());
        assertEquals(source.getPrimitiveCharField(), destination.getDestPrimitiveCharField());
        assertEquals(source.getPrimitiveIntField(), destination.getDestPrimitiveIntField());
        assertEquals(source.getDoubleField(), destination.getDestDoubleField());
        assertEquals(source.getBigDecimalField(), destination.getDestBigDecimalField());
    }


}