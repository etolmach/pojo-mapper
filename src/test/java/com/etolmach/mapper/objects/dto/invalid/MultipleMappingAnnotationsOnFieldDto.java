package com.etolmach.mapper.objects.dto.invalid;

import com.etolmach.mapper.annotations.FieldMapping;
import com.etolmach.mapper.annotations.MethodMapping;
import lombok.Getter;

/**
 * @author etolmach
 */
@Getter
public class MultipleMappingAnnotationsOnFieldDto {
    @FieldMapping(name = "stringField")
    @MethodMapping(name = "getStringField")
    private String testFoo = "testFoo";
}
