package com.etolmach.mapper.objects.dto.invalid;

import com.etolmach.mapper.annotations.FieldMapping;
import lombok.Getter;
import org.apache.commons.jxpath.util.BasicTypeConverter;

/**
 * @author etolmach
 */
@Getter
public class MultipleConvertersOnFieldMappingDto {
    String testFoo = "testFoo";

    @FieldMapping(name = "stringField", converter = BasicTypeConverter.class, converterName = "testConverterName")
    public void setTestFoo(String testFoo) {
        this.testFoo = testFoo;
    }
}
