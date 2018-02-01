package com.etolmach.mapper.objects;

import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import static com.etolmach.mapper.MapperUtils.field;
import static com.etolmach.mapper.MapperUtils.method;

/**
 * @author etolmach
 */
@Data
public class Destination {

    public static final Field STRING_FIELD = field(Destination.class, "destStringField");
    public static final Field PRIMITIVE_CHAR_FIELD = field(Destination.class, "destPrimitiveCharField");
    public static final Field PRIMITIVE_INT_FIELD = field(Destination.class, "destPrimitiveIntField");
    public static final Field DOUBLE_FIELD = field(Destination.class, "destDoubleField");
    public static final Field BIG_DECIMAL_FIELD = field(Destination.class, "destBigDecimalField");

    public static final Field INACCESSIBLE_FIELD = field(Destination.class, "inaccessibleDestStringField", false);

    public static final Method SET_STRING_METHOD = method(Destination.class, "setDestStringField", String.class);
    public static final Method SET_PRIMITIVE_CHAR_METHOD = method(Destination.class, "setDestPrimitiveCharField", char.class);
    public static final Method SET_PRIMITIVE_INT_METHOD = method(Destination.class, "setDestPrimitiveIntField", int.class);
    public static final Method SET_DOUBLE_METHOD = method(Destination.class, "setDestDoubleField", Double.class);
    public static final Method SET_BIG_DECIMAL_METHOD = method(Destination.class, "setDestBigDecimalField", BigDecimal.class);

    public static final Method INACCESSIBLE_METHOD = method(Destination.class, "inaccessibleMethod", false, String.class);

    private String destStringField;
    private char destPrimitiveCharField;
    private int destPrimitiveIntField;
    private Double destDoubleField;
    private BigDecimal destBigDecimalField;

    private String inaccessibleDestStringField;

    private void inaccessibleMethod(String string) {
        this.inaccessibleDestStringField = string;
    }


}
