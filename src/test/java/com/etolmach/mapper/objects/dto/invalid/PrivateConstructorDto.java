package com.etolmach.mapper.objects.dto.invalid;

import com.etolmach.mapper.annotations.FieldMapping;
import lombok.Data;

/**
 * @author etolmach
 */
@Data
public class PrivateConstructorDto {
    @FieldMapping(name = "stringField")
    private String testStringField;

    private PrivateConstructorDto() {
    }
}
