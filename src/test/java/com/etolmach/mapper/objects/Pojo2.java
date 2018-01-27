package com.etolmach.mapper.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author etolmach
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Pojo2 {
    private Double doubleField;
    private BigDecimal bigDecimalField;
}
