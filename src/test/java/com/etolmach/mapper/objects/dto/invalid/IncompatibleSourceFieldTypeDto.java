package com.etolmach.mapper.objects.dto.invalid;

import com.etolmach.mapper.annotations.FieldMapping;
import lombok.Getter;

/**
 * @author etolmach
 */
@Getter
public class IncompatibleSourceFieldTypeDto {

    String testFoo = "testFoo";

    @FieldMapping(name = "primitiveIntField")
    public void setTestFoo(String testFoo) {
        this.testFoo = testFoo;
    }
}
