package com.wesleyadiel.valeapi.mapper;

import com.wesleyadiel.valeapi.dto.request.UserRequest;
import com.wesleyadiel.valeapi.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequest dto);
}