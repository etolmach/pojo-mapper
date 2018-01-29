package com.etolmach.mapper.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author etolmach
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Pojo implements  PojoInterface{
    private String stringField;
    private char primitiveCharField;
    private int primitiveIntField;
    private Double doubleField;
    private BigDecimal bigDecimalField;

    private String privateMethod(){
        return null;
    }
}
