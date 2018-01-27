package com.etolmach.mapper.objects.dto.valid.explicit;

import com.etolmach.mapper.annotations.MethodMapping;
import com.etolmach.mapper.objects.Pojo;
import com.etolmach.mapper.objects.Pojo2;
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
    @MethodMapping(name = "getPrimitiveCharField", source = Pojo.class)
    private char testPrimitiveCharField;
    // Non-matching source class
    @MethodMapping(name = "getPrimitiveIntField", source = Pojo2.class)
    private int testPrimitiveIntField;
}
