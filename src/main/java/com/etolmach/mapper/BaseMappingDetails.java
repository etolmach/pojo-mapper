package com.etolmach.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.jxpath.util.TypeConverter;

import java.lang.reflect.Member;

/**
 * @author etolmach
 */
@Data
@AllArgsConstructor
public class BaseMappingDetails<S, D> implements MappingDetails<S, D> {

    private Member srcMember;

    private Member destMember;

    private Class<?> destMemberType;

    private TypeConverter converter;

}
