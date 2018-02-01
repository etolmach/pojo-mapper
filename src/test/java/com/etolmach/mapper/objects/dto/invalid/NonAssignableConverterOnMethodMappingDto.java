package com.etolmach.mapper.objects.dto.invalid;

import com.etolmach.mapper.annotations.MethodMapping;
import com.etolmach.mapper.converter.NonAssignableConverter;
import lombok.Getter;

/**
 * @author etolmach
 */
@Getter
public class NonAssignableConverterOnMethodMappingDto {
    String testFoo = "testFoo";

    @MethodMapping(name = "getStringField", converter = NonAssignableConverter.class)
    public void setTestFoo(String testFoo) {
        this.testFoo = testFoo;
    }
}
