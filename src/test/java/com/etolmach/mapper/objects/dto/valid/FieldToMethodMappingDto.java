package com.etolmach.mapper.objects.dto.valid;

import com.etolmach.mapper.annotations.FieldMapping;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author etolmach
 */
@Data
public class FieldToMethodMappingDto {

    private String testStringField;
    private char testPrimitiveCharField;
    private int testPrimitiveIntField;
    private Double testDoubleField;
    private BigDecimal testBigDecimalField;

    @FieldMapping(name = "stringField")
    public void setTestStringField(String testStringField) {
        this.testStringField = testStringField;
    }

    @FieldMapping(name = "primitiveCharField")
    public void setTestPrimitiveCharField(char testPrimitiveCharField) {
        this.testPrimitiveCharField = testPrimitiveCharField;
    }

    @FieldMapping(name = "primitiveIntField")
    public void setTestPrimitiveIntField(int testPrimitiveIntField) {
        this.testPrimitiveIntField = testPrimitiveIntField;
    }

    @FieldMapping(name = "doubleField")
    public void setTestDoubleField(Double testDoubleField) {
        this.testDoubleField = testDoubleField;
    }

    @FieldMapping(name = "bigDecimalField")
    public void setTestBigDecimalField(BigDecimal testBigDecimalField) {
        this.testBigDecimalField = testBigDecimalField;
    }
}
