package com.etolmach.mapper.objects.dto.valid;

import com.etolmach.mapper.annotations.MethodMapping;
import lombok.Getter;

/**
 * @author etolmach
 */
@Getter
public class NamedConverterOnMethodMappingDto {
    String testFoo = "testFoo";

    @MethodMapping(name = "getStringField", converterName = "testConverterName")
    public void setTestFoo(String testFoo) {
        this.testFoo = testFoo;
    }
}
