package com.etolmach.mapper.objects.dto.invalid;

import com.etolmach.mapper.annotations.MethodMapping;
import lombok.Getter;
import org.apache.commons.jxpath.util.BasicTypeConverter;

/**
 * @author etolmach
 */
@Getter
public class MultipleConvertersOnMethodMappingDto {
    String testFoo = "testFoo";

    @MethodMapping(name = "getStringField", converter = BasicTypeConverter.class, converterName = "testConverterName")
    public void setTestFoo(String testFoo) {
        this.testFoo = testFoo;
    }
}
