package com.etolmach.mapper;

import com.etolmach.mapper.exceptions.MapperConfigurationException;

import java.util.List;
import java.util.Map;

/**
 * @author etolmach
 */
public interface MapperBuilder {

    <S, D> Mapper<S, D> build(S srcObject, D destObject) throws MapperConfigurationException;

    <S, D> Mapper<S, D> build(Class<S> srcClass, Class<D> destClass) throws MapperConfigurationException;

    <D> Map<Class<?>, Mapper<?, D>> buildAll(Class<D> destClass, Class<?>... srcClasses) throws MapperConfigurationException;

    <D> Map<Class<?>, Mapper<?, D>> buildAll(Class<D> destClass, List<Class<?>> srcClasses) throws MapperConfigurationException;
}
