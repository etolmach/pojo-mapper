package com.etolmach.mapper.objects.dto.valid;

import com.etolmach.mapper.annotations.MethodMapping;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author etolmach
 */
@Data
public class MethodToFieldMappingDto {
    @MethodMapping(name = "getStringField")
    private String testStringField;
    @MethodMapping(name = "getPrimitiveCharField")
    private char testPrimitiveCharField;
    @MethodMapping(name = "getPrimitiveIntField")
    private int testPrimitiveIntField;
    @MethodMapping(name = "getDoubleField")
    private Double testDoubleField;
    @MethodMapping(name = "getBigDecimalField")
    private BigDecimal testBigDecimalField;
}
