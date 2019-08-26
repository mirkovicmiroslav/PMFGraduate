package com.pmfgraduate.mapper;

import com.pmfgraduate.dto.RegisterDTO;
import com.pmfgraduate.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User map(RegisterDTO source);
}
