package com.etolmach.mapper;

import org.apache.commons.jxpath.util.TypeConverter;

import java.lang.reflect.Member;

/**
 * @author etolmach
 */
public interface MappingDetails {

    Member getSrcMember();

    Member getDestMember();

    Class<?> getDestMemberType();

    TypeConverter getConverter();
}

