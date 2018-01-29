package com.etolmach.mapper.annotations;

import org.apache.commons.jxpath.util.BasicTypeConverter;
import org.apache.commons.jxpath.util.TypeConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author etolmach
 */
@Target(value = {ElementType.METHOD, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface MethodMapping {

    String name();

    Class<?> source() default Object.class;

    Class<? extends TypeConverter> converter() default TypeConverter.class;

    String converterName() default "";

}
