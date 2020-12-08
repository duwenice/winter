package com.dw.winter.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author duwen
 * @date 2020/12/8 13:21:09
 */
@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(source = "name", target = "userName")
    PersonDTO personToPersonDTO(Person person);
}
