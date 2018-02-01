package com.etolmach.mapper.objects.dto.valid;

import com.etolmach.mapper.annotations.MethodMapping;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author etolmach
 */
@Data
public class MethodToMethodMappingDto {

    private String testStringField;
    private char testPrimitiveCharField;
    private int testPrimitiveIntField;
    private Double testDoubleField;
    private BigDecimal testBigDecimalField;

    @MethodMapping(name = "getStringField")
    public void setTestStringField(String testStringField) {
        this.testStringField = testStringField;
    }

    @MethodMapping(name = "getPrimitiveCharField")
    public void setTestPrimitiveCharField(char testPrimitiveCharField) {
        this.testPrimitiveCharField = testPrimitiveCharField;
    }

    @MethodMapping(name = "getPrimitiveIntField")
    public void setTestPrimitiveIntField(int testPrimitiveIntField) {
        this.testPrimitiveIntField = testPrimitiveIntField;
    }

    @MethodMapping(name = "getDoubleField")
    public void setTestDoubleField(Double testDoubleField) {
        this.testDoubleField = testDoubleField;
    }

    @MethodMapping(name = "getBigDecimalField")
    public void setTestBigDecimalField(BigDecimal testBigDecimalField) {
        this.testBigDecimalField = testBigDecimalField;
    }

    private void privateMethod(String string) {
        this.testStringField = string;
    }
}
