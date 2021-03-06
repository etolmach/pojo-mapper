package com.etolmach.mapper.objects.dto.valid.explicit;

import com.etolmach.mapper.annotations.MethodMapping;
import com.etolmach.mapper.objects.Destination;
import com.etolmach.mapper.objects.PojoInterface;
import lombok.Data;

/**
 * @author etolmach
 */
@Data
public class ExplicitMethodToFieldMappingDto {
    // Default source class
    @MethodMapping(name = "getStringField")
    private String testStringField;
    // Explicit source class
    @MethodMapping(name = "getPrimitiveCharField", source = PojoInterface.class)
    private char testPrimitiveCharField;
    // Non-matching source class
    @MethodMapping(name = "getPrimitiveIntField", source = Destination.class)
    private int testPrimitiveIntField;
}
