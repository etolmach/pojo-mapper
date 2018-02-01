package com.etolmach.mapper.objects.dto.valid.explicit;

import com.etolmach.mapper.annotations.FieldMapping;
import com.etolmach.mapper.objects.Destination;
import com.etolmach.mapper.objects.Source;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author etolmach
 */
@Data
public class MultipleSourceFieldToFieldMappingDto {
    // Default source class
    @FieldMapping(name = "stringField", source = Source.class)
    private String testStringField;
    // Explicit source class
    @FieldMapping(name = "primitiveCharField", source = Source.class)
    private char testPrimitiveCharField;
    @FieldMapping(name = "primitiveIntField", source = Source.class)
    private int testPrimitiveIntField;
    @FieldMapping(name = "doubleField", source = Destination.class)
    private Double testDoubleField;
    @FieldMapping(name = "bigDecimalField", source = Destination.class)
    private BigDecimal testBigDecimalField;
}
