package com.etolmach.mapper.objects.dto.valid.explicit;

import com.etolmach.mapper.annotations.FieldMapping;
import com.etolmach.mapper.objects.Destination;
import com.etolmach.mapper.objects.Source;
import lombok.Data;

/**
 * @author etolmach
 */
@Data
public class ExplicitFieldToFieldMappingDto {
    // Default source class
    @FieldMapping(name = "stringField")
    private String testStringField;
    // Explicit source class
    @FieldMapping(name = "primitiveCharField", source = Source.class)
    private char testPrimitiveCharField;
    // Non-matching source class
    @FieldMapping(name = "primitiveIntField", source = Destination.class)
    private int testPrimitiveIntField;
}
