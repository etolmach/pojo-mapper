package com.etolmach.mapper.objects;

import java.math.BigDecimal;

/**
 * @author etolmach
 */
public interface PojoInterface {

    String getStringField();

    char getPrimitiveCharField();

    int getPrimitiveIntField();

    Double getDoubleField();

    BigDecimal getBigDecimalField();
}
