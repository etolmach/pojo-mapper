package com.etolmach.mapper;

import com.etolmach.mapper.converter.CachedConverterByTypeProvider;
import com.etolmach.mapper.converter.ConverterByNameProvider;
import com.etolmach.mapper.converter.ConverterByTypeProvider;
import com.etolmach.mapper.exceptions.CannotInjectValueException;
import com.etolmach.mapper.exceptions.CannotInstantiateDestinationObjectException;
import com.etolmach.mapper.exceptions.CannotRetrieveSourceValueException;
import com.etolmach.mapper.exceptions.MapperException;
import com.etolmach.mapper.objects.Pojo;
import com.etolmach.mapper.objects.Pojo2;
import com.etolmach.mapper.objects.PojoInterface;
import com.etolmach.mapper.objects.dto.invalid.PrivateConstructorDto;
import com.etolmach.mapper.objects.dto.valid.FieldToFieldMappingDto;
import com.etolmach.mapper.objects.dto.valid.FieldToMethodMappingDto;
import com.etolmach.mapper.objects.dto.valid.MethodToFieldMappingDto;
import com.etolmach.mapper.objects.dto.valid.MethodToMethodMappingDto;
import com.etolmach.mapper.objects.dto.valid.explicit.ExplicitFieldToFieldMappingDto;
import com.etolmach.mapper.objects.dto.valid.explicit.ExplicitMethodToFieldMappingDto;
import com.etolmach.mapper.objects.dto.valid.explicit.MultipleSourceFieldToFieldMappingDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

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
    private static final Double DOUBLE_2 = 9876543.21;
    private static final BigDecimal BIG_DECIMAL_2 = new BigDecimal("9876543210.123456789");

    private ConverterByTypeProvider converterByTypeProvider = new CachedConverterByTypeProvider();
    private ConverterByNameProvider converterByNameProvider = null;
    private final DefaultMapperBuilder builder = new DefaultMapperBuilder(converterByTypeProvider, converterByNameProvider);
    private final Pojo pojo = new Pojo(STRING, PRIMITIVE_CHAR, PRIMITIVE_INT, DOUBLE, BIG_DECIMAL);
    private final Pojo2 pojo2 = new Pojo2(DOUBLE_2, BIG_DECIMAL_2);

    @Test
    public void validFieldToFieldMapping() throws MapperException {
        Mapper<Pojo, FieldToFieldMappingDto> mapper = builder.build(Pojo.class, FieldToFieldMappingDto.class);
        FieldToFieldMappingDto dto = new FieldToFieldMappingDto();

        mapper.map(pojo, dto);

        assertEquals(pojo.getStringField(), dto.getTestStringField());
        assertEquals(pojo.getPrimitiveCharField(), dto.getTestPrimitiveCharField());
        assertEquals(pojo.getPrimitiveIntField(), dto.getTestPrimitiveIntField());
        assertEquals(pojo.getDoubleField(), dto.getTestDoubleField());
        assertEquals(pojo.getBigDecimalField(), dto.getTestBigDecimalField());
    }

    @Test
    public void validMethodToFieldMapping() throws MapperException {
        Mapper<Pojo, MethodToFieldMappingDto> mapper = builder.build(Pojo.class, MethodToFieldMappingDto.class);
        MethodToFieldMappingDto dto = new MethodToFieldMappingDto();

        mapper.map(pojo, dto);

        assertEquals(pojo.getStringField(), dto.getTestStringField());
        assertEquals(pojo.getPrimitiveCharField(), dto.getTestPrimitiveCharField());
        assertEquals(pojo.getPrimitiveIntField(), dto.getTestPrimitiveIntField());
        assertEquals(pojo.getDoubleField(), dto.getTestDoubleField());
        assertEquals(pojo.getBigDecimalField(), dto.getTestBigDecimalField());
    }

    @Test
    public void validFieldToMethodMapping() throws MapperException {
        Mapper<Pojo, FieldToMethodMappingDto> mapper = builder.build(Pojo.class, FieldToMethodMappingDto.class);
        FieldToMethodMappingDto dto = new FieldToMethodMappingDto();

        mapper.map(pojo, dto);

        assertEquals(pojo.getStringField(), dto.getTestStringField());
        assertEquals(pojo.getPrimitiveCharField(), dto.getTestPrimitiveCharField());
        assertEquals(pojo.getPrimitiveIntField(), dto.getTestPrimitiveIntField());
        assertEquals(pojo.getDoubleField(), dto.getTestDoubleField());
        assertEquals(pojo.getBigDecimalField(), dto.getTestBigDecimalField());
    }

    @Test
    public void mapToNewObject() throws MapperException {
        Mapper<Pojo, FieldToMethodMappingDto> mapper = builder.build(Pojo.class, FieldToMethodMappingDto.class);

        FieldToMethodMappingDto dto = mapper.map(pojo);

        assertEquals(pojo.getStringField(), dto.getTestStringField());
        assertEquals(pojo.getPrimitiveCharField(), dto.getTestPrimitiveCharField());
        assertEquals(pojo.getPrimitiveIntField(), dto.getTestPrimitiveIntField());
        assertEquals(pojo.getDoubleField(), dto.getTestDoubleField());
        assertEquals(pojo.getBigDecimalField(), dto.getTestBigDecimalField());
    }

    @Test(expected = CannotInstantiateDestinationObjectException.class)
    public void cannotInstantiateDestinationObject() throws MapperException {
        Mapper<Pojo, PrivateConstructorDto> mapper = builder.build(Pojo.class, PrivateConstructorDto.class);

        mapper.map(pojo);
    }

    @Test
    public void validMethodToMethodMapping() throws MapperException {
        Mapper<Pojo, MethodToMethodMappingDto> mapper = builder.build(Pojo.class, MethodToMethodMappingDto.class);
        MethodToMethodMappingDto dto = new MethodToMethodMappingDto();

        mapper.map(pojo, dto);

        assertEquals(pojo.getStringField(), dto.getTestStringField());
        assertEquals(pojo.getPrimitiveCharField(), dto.getTestPrimitiveCharField());
        assertEquals(pojo.getPrimitiveIntField(), dto.getTestPrimitiveIntField());
        assertEquals(pojo.getDoubleField(), dto.getTestDoubleField());
        assertEquals(pojo.getBigDecimalField(), dto.getTestBigDecimalField());
    }

    @Test(expected = CannotRetrieveSourceValueException.class)
    public void cannotRetrieveSourceValueFromField() throws MapperException, NoSuchFieldException {
//        Map<Member, Member> map = new HashMap<>();
//        // The source field is inaccessible
//        Field srcField = Pojo.class.getDeclaredField("stringField");
//        Field destField = FieldToFieldMappingDto.class.getDeclaredField("testStringField");
//        map.put(destField, srcField);
//
//        Mapper<Pojo, FieldToMethodMappingDto> mapper = new DefaultMapper<>(Pojo.class, FieldToMethodMappingDto.class, map);
//
//        mapper.map(pojo);
        assertFalse(true);
    }

    @Test(expected = CannotRetrieveSourceValueException.class)
    public void cannotRetrieveSourceValueFromMethod() throws MapperException, NoSuchFieldException, NoSuchMethodException {
//        Map<Member, Member> map = new HashMap<>();
//        // The source method is inaccessible
//        Method srcMethod = Pojo.class.getDeclaredMethod("privateMethod");
//        Field destField = FieldToFieldMappingDto.class.getDeclaredField("testStringField");
//        map.put(destField, srcMethod);
//
//        Mapper<Pojo, FieldToMethodMappingDto> mapper = new DefaultMapper<>(Pojo.class, FieldToMethodMappingDto.class, map);
//
//        mapper.map(pojo);
        assertFalse(true);
    }

    @Test(expected = CannotInjectValueException.class)
    public void cannotInjectValueToField() throws MapperException, NoSuchFieldException, NoSuchMethodException {
//        Map<Member, Member> map = new HashMap<>();
//        // The source method is accessible
//        Method srcMethod = Pojo.class.getDeclaredMethod("getStringField");
//        // The target field is inaccessible
//        Field destField = MethodToFieldMappingDto.class.getDeclaredField("testStringField");
//        map.put(destField, srcMethod);
//
//        Mapper<Pojo, MethodToFieldMappingDto> mapper = new DefaultMapper<>(Pojo.class, MethodToFieldMappingDto.class, map);
//
//        mapper.map(pojo);
        assertFalse(true);
    }

    @Test(expected = CannotInjectValueException.class)
    public void cannotInjectValueToMethod() throws MapperException, NoSuchMethodException {
//        Map<Member, Member> map = new HashMap<>();
//        // The source method is accessible
//        Method srcMethod = Pojo.class.getDeclaredMethod("getStringField");
//        // The target field is inaccessible
//        Method destMethod = MethodToMethodMappingDto.class.getDeclaredMethod("privateMethod", String.class);
//        map.put(destMethod, srcMethod);
//
//        Mapper<Pojo, MethodToMethodMappingDto> mapper = new DefaultMapper<>(Pojo.class, MethodToMethodMappingDto.class, map);
//
//        mapper.map(pojo);
        assertFalse(true);
    }

    @Test
    public void explicitSourceFieldMappingOnFieldDto() throws MapperException {
        Mapper<Pojo, ExplicitFieldToFieldMappingDto> mapper = builder.build(Pojo.class, ExplicitFieldToFieldMappingDto.class);

        ExplicitFieldToFieldMappingDto dto = mapper.map(pojo);

        assertEquals(pojo.getStringField(), dto.getTestStringField());
        assertEquals(pojo.getPrimitiveCharField(), dto.getTestPrimitiveCharField());
        assertNotEquals(pojo.getPrimitiveIntField(), dto.getTestPrimitiveIntField());
    }

    @Test
    public void explicitSourceMethodMappingOnFieldDto() throws MapperException {
        Mapper<Pojo, ExplicitMethodToFieldMappingDto> mapper = this.builder.build(Pojo.class, ExplicitMethodToFieldMappingDto.class);

        ExplicitMethodToFieldMappingDto dto = mapper.map(pojo);

        assertEquals(pojo.getStringField(), dto.getTestStringField());
        assertEquals(pojo.getPrimitiveCharField(), dto.getTestPrimitiveCharField());
        assertNotEquals(pojo.getPrimitiveIntField(), dto.getTestPrimitiveIntField());
    }

    @Test
    public void explicitSourceInterfaceMethodMappingOnFieldDto() throws MapperException {
        Mapper<PojoInterface, ExplicitMethodToFieldMappingDto> mapper = this.builder.build(PojoInterface.class, ExplicitMethodToFieldMappingDto.class);

        ExplicitMethodToFieldMappingDto dto = mapper.map(pojo);

        assertEquals(pojo.getStringField(), dto.getTestStringField());
        assertEquals(pojo.getPrimitiveCharField(), dto.getTestPrimitiveCharField());
        assertNotEquals(pojo.getPrimitiveIntField(), dto.getTestPrimitiveIntField());
    }

    @Test
    public void buildAll() throws MapperException {
        Map<Class<?>, Mapper<?, MultipleSourceFieldToFieldMappingDto>> mappers = builder.buildAll(MultipleSourceFieldToFieldMappingDto.class, Pojo.class, Pojo2.class);

        MultipleSourceFieldToFieldMappingDto dto = new MultipleSourceFieldToFieldMappingDto();

        Mapper<Pojo, MultipleSourceFieldToFieldMappingDto> pojoMapper = (Mapper<Pojo, MultipleSourceFieldToFieldMappingDto>) mappers.get(Pojo.class);
        Mapper<Pojo2, MultipleSourceFieldToFieldMappingDto> pojo2Mapper = (Mapper<Pojo2, MultipleSourceFieldToFieldMappingDto>) mappers.get(Pojo2.class);

        pojoMapper.map(pojo, dto);

        assertEquals(pojo.getStringField(), dto.getTestStringField());
        assertEquals(pojo.getPrimitiveCharField(), dto.getTestPrimitiveCharField());
        assertEquals(pojo.getPrimitiveIntField(), dto.getTestPrimitiveIntField());
        assertNotEquals(pojo.getDoubleField(), dto.getTestDoubleField());
        assertNotEquals(pojo.getBigDecimalField(), dto.getTestBigDecimalField());

        pojo2Mapper.map(pojo2, dto);

        assertEquals(pojo.getStringField(), dto.getTestStringField());
        assertEquals(pojo.getPrimitiveCharField(), dto.getTestPrimitiveCharField());
        assertEquals(pojo.getPrimitiveIntField(), dto.getTestPrimitiveIntField());
        assertEquals(pojo2.getDoubleField(), dto.getTestDoubleField());
        assertEquals(pojo2.getBigDecimalField(), dto.getTestBigDecimalField());
    }

}