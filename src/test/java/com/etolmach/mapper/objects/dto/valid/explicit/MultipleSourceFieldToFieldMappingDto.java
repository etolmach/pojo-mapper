package com.etolmach.mapper.objects.dto.valid.explicit;

import com.etolmach.mapper.annotations.FieldMapping;
import com.etolmach.mapper.objects.Pojo;
import com.etolmach.mapper.objects.Pojo2;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author etolmach
 */
@Data
public class MultipleSourceFieldToFieldMappingDto {
    // Default source class
    @FieldMapping(name = "stringField", source = Pojo.class)
    private String testStringField;
    // Explicit source class
    @FieldMapping(name = "primitiveCharField", source = Pojo.class)
    private char testPrimitiveCharField;
    @FieldMapping(name = "primitiveIntField", source = Pojo.class)
    private int testPrimitiveIntField;
    @FieldMapping(name = "doubleField", source = Pojo2.class)
    private Double testDoubleField;
    @FieldMapping(name = "bigDecimalField", source = Pojo2.class)
    private BigDecimal testBigDecimalField;
}
