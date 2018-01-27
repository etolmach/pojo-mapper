package com.etolmach.mapper.objects.dto.invalid;

import com.etolmach.mapper.annotations.FieldMapping;
import com.etolmach.mapper.annotations.MethodMapping;
import lombok.Getter;

/**
 * @author etolmach
 */
@Getter
public class MultipleMappingAnnotationsOnMethodDto {
    String testFoo = "testFoo";

    @FieldMapping(name = "stringField")
    @MethodMapping(name = "getStringField")
    public void setTestFoo(String testFoo) {
        this.testFoo = testFoo;
    }
}
