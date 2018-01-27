package com.etolmach.mapper.objects.dto.invalid;

import com.etolmach.mapper.annotations.FieldMapping;
import lombok.Getter;

/**
 * @author etolmach
 */
@Getter
public class NonExistingSourceFieldDto {

    String testFoo = "testFoo";

    @FieldMapping(name = "nonExistentFieldName")
    public void setTestFoo(String testFoo) {
        this.testFoo = testFoo;
    }
}
