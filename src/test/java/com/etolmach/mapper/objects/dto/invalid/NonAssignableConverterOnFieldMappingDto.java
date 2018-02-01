package com.etolmach.mapper.objects.dto.invalid;

import com.etolmach.mapper.annotations.FieldMapping;
import com.etolmach.mapper.converter.NonAssignableConverter;
import lombok.Getter;

/**
 * @author etolmach
 */
@Getter
public class NonAssignableConverterOnFieldMappingDto {
    String testFoo = "testFoo";

    @FieldMapping(name = "stringField", converter = NonAssignableConverter.class)
    public void setTestFoo(String testFoo) {
        this.testFoo = testFoo;
    }
}
