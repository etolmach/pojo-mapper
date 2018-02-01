package com.etolmach.mapper.objects.dto.valid.explicit;

import com.etolmach.mapper.annotations.FieldMapping;
import com.etolmach.mapper.annotations.MethodMapping;
import com.etolmach.mapper.objects.Source;
import com.etolmach.mapper.objects.Source2;
import lombok.Data;
import org.apache.commons.jxpath.util.BasicTypeConverter;

import java.math.BigDecimal;

/**
 * @author etolmach
 */
@Data
public class MultipleSourcesMixedMappingDto {
    // Default source class
    @FieldMapping(name = "stringField", source = Source.class)
    private String testStringField;
    // Explicit source class
    @MethodMapping(name = "getPrimitiveCharField", source = Source.class, converter = BasicTypeConverter.class)
    private char testPrimitiveCharField;
    @FieldMapping(name = "primitiveIntField", source = Source.class)
    private int testPrimitiveIntField;

    private Double testDoubleField;
    private BigDecimal testBigDecimalField;

    @MethodMapping(name = "getDoubleField", source = Source2.class)
    public void setTestDoubleField(Double doubleField) {
        this.testDoubleField = doubleField;
    }

    @FieldMapping(name = "bigDecimalField", source = Source2.class, converterName = "test")
    public void setTestBigDecimalField(BigDecimal bigDecimalField) {
        this.testBigDecimalField = bigDecimalField;
    }
}
