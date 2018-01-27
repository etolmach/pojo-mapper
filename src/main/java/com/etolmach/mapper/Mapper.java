package com.etolmach.mapper;

import com.etolmach.mapper.exceptions.MapperExecutionException;

/**
 * @author etolmach
 */
public interface Mapper<S, D> {

    D map(S srcObject) throws MapperExecutionException;

    D map(S srcObject, D destObject) throws MapperExecutionException;
}
