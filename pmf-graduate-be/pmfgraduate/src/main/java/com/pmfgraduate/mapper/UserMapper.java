package com.pmfgraduate.mapper;

import org.mapstruct.Mapper;

import com.pmfgraduate.dto.RegisterDTO;
import com.pmfgraduate.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	User map(RegisterDTO source);
}
