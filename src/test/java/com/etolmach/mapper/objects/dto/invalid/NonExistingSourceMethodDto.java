package com.etolmach.mapper.objects.dto.invalid;

import com.etolmach.mapper.annotations.MethodMapping;
import lombok.Getter;

/**
 * @author etolmach
 */
@Getter
public class NonExistingSourceMethodDto {

    String testFoo = "testFoo";

    @MethodMapping(name = "nonExistentMethodName")
    public void setTestFoo(String testFoo) {
        this.testFoo = testFoo;
    }
}
