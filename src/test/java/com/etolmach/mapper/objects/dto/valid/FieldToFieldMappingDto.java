package com.etolmach.mapper.objects.dto.valid;

import com.etolmach.mapper.annotations.FieldMapping;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author etolmach
 */
@Data
public class FieldToFieldMappingDto {
    @FieldMapping(name = "stringField")
    private String testStringField;
    @FieldMapping(name = "primitiveCharField")
    private char testPrimitiveCharField;
    @FieldMapping(name = "primitiveIntField")
    private int testPrimitiveIntField;
    @FieldMapping(name = "doubleField")
    private Double testDoubleField;
    @FieldMapping(name = "bigDecimalField")
    private BigDecimal testBigDecimalField;
}
