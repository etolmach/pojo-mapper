package com.etolmach.mapper.objects.dto.invalid;

import com.etolmach.mapper.annotations.MethodMapping;
import lombok.Getter;

/**
 * @author etolmach
 */
@Getter
public class IncompatibleSourceMethodTypeDto {

    String testFoo = "testFoo";

    @MethodMapping(name = "getPrimitiveIntField")
    public void setTestFoo(String testFoo) {
        this.testFoo = testFoo;
    }
}
