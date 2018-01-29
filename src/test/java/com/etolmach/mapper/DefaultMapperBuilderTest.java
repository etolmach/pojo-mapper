package com.etolmach.mapper;

import com.etolmach.mapper.converter.CachedConverterByTypeProvider;
import com.etolmach.mapper.converter.ConverterByNameProvider;
import com.etolmach.mapper.converter.ConverterByTypeProvider;
import com.etolmach.mapper.exceptions.IncompatibleTypesException;
import com.etolmach.mapper.exceptions.InvalidSourceMemberException;
import com.etolmach.mapper.exceptions.MapperConfigurationException;
import com.etolmach.mapper.exceptions.MultipleMappingAnnotationsException;
import com.etolmach.mapper.objects.Pojo;
import com.etolmach.mapper.objects.dto.invalid.*;
import com.etolmach.mapper.objects.dto.valid.FieldToFieldMappingDto;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author etolmach
 */
public class DefaultMapperBuilderTest {

    private ConverterByTypeProvider converterByTypeProvider = new CachedConverterByTypeProvider();
    private ConverterByNameProvider converterByNameProvider = null;
    private final DefaultMapperBuilder builder = new DefaultMapperBuilder(converterByTypeProvider, converterByNameProvider);

    @Test
    public void buildByClasses() throws MapperConfigurationException {
        builder.build(Pojo.class, FieldToFieldMappingDto.class);
    }

    @Test
    public void buildByTypes() throws MapperConfigurationException {
        DefaultMapperBuilder spyBuilder = Mockito.spy(builder);

        spyBuilder.build(new Pojo(), new FieldToFieldMappingDto());

        verify(spyBuilder, times(1)).build(Pojo.class, FieldToFieldMappingDto.class);
    }

    @Test(expected = InvalidSourceMemberException.class)
    public void nonExistingSourceField() throws MapperConfigurationException {
        builder.build(Pojo.class, NonExistingSourceFieldDto.class);
    }

    @Test(expected = InvalidSourceMemberException.class)
    public void nonExistingSourceMethod() throws MapperConfigurationException {
        builder.build(Pojo.class, NonExistingSourceMethodDto.class);
    }

    @Test(expected = IncompatibleTypesException.class)
    public void incompatibleSourceFieldTypeDto() throws MapperConfigurationException {
        builder.build(Pojo.class, IncompatibleSourceFieldTypeDto.class);
    }

    @Test(expected = IncompatibleTypesException.class)
    public void incompatibleSourceMethodTypeDto() throws MapperConfigurationException {
        builder.build(Pojo.class, IncompatibleSourceMethodTypeDto.class);
    }

    @Test(expected = MultipleMappingAnnotationsException.class)
    public void multipleMappingAnnotationsOnFieldDto() throws MapperConfigurationException {
        builder.build(Pojo.class, MultipleMappingAnnotationsOnFieldDto.class);
    }

    @Test(expected = MultipleMappingAnnotationsException.class)
    public void multipleMappingAnnotationsOnMethodDto() throws MapperConfigurationException {
        builder.build(Pojo.class, MultipleMappingAnnotationsOnMethodDto.class);
    }

}