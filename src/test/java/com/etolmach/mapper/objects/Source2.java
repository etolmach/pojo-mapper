package com.etolmach.mapper.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import static com.etolmach.mapper.MapperUtils.field;
import static com.etolmach.mapper.MapperUtils.method;

/**
 * @author etolmach
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Source2 implements PojoInterface {

    public static final Field STRING_FIELD = field(Source2.class, "stringField");
    public static final Field PRIMITIVE_CHAR_FIELD = field(Source2.class, "primitiveCharField");
    public static final Field PRIMITIVE_INT_FIELD = field(Source2.class, "primitiveIntField");
    public static final Field DOUBLE_FIELD = field(Source2.class, "doubleField");
    public static final Field BIG_DECIMAL_FIELD = field(Source2.class, "bigDecimalField");

    public static final Method GET_STRING_METHOD = method(Source2.class, "getStringField");
    public static final Method GET_PRIMITIVE_CHAR_METHOD = method(Source2.class, "getPrimitiveCharField");
    public static final Method GET_PRIMITIVE_INT_METHOD = method(Source2.class, "getPrimitiveIntField");
    public static final Method GET_DOUBLE_METHOD = method(Source2.class, "getDoubleField");
    public static final Method GET_BIG_DECIMAL_METHOD = method(Source2.class, "getBigDecimalField");

    private String stringField;
    private char primitiveCharField;
    private int primitiveIntField;
    private Double doubleField;
    private BigDecimal bigDecimalField;

}
