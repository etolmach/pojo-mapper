package com.etolmach.mapper.objects.dto.valid;

import com.etolmach.mapper.annotations.FieldMapping;
import lombok.Getter;

/**
 * @author etolmach
 */
@Getter
public class NamedConverterOnFieldMappingDto {

    public static final String CONVERTER_NAME = "testConverterName";

    private String testStringField = "testStringField";

    @FieldMapping(name = "stringField", converterName = CONVERTER_NAME)
    public void setTestStringField(String stringField) {
        this.testStringField = stringField;
    }
}
