package ru.example.letterflow.service.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.example.letterflow.domain.dto.UserDto;
import ru.example.letterflow.domain.entity.User;

@Mapper
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
