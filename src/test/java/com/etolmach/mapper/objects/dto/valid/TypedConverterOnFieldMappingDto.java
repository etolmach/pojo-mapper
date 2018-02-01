package com.etolmach.mapper.objects.dto.valid;

import com.etolmach.mapper.annotations.FieldMapping;
import lombok.Getter;
import org.apache.commons.jxpath.util.BasicTypeConverter;

/**
 * @author etolmach
 */
@Getter
public class TypedConverterOnFieldMappingDto {
    String testFoo = "testFoo";

    @FieldMapping(name = "stringField", converter = BasicTypeConverter.class)
    public void setTestFoo(String testFoo) {
        this.testFoo = testFoo;
    }
}
