package com.etolmach.mapper.objects.dto.valid;

import com.etolmach.mapper.annotations.MethodMapping;
import lombok.Getter;
import org.apache.commons.jxpath.util.BasicTypeConverter;

/**
 * @author etolmach
 */
@Getter
public class TypedConverterOnMethodMappingDto {
    String testFoo = "testFoo";

    @MethodMapping(name = "getStringField", converter = BasicTypeConverter.class)
    public void setTestFoo(String testFoo) {
        this.testFoo = testFoo;
    }
}
