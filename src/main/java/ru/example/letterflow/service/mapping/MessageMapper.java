package ru.example.letterflow.service.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.example.letterflow.domain.dto.MessageDto;
import ru.example.letterflow.domain.entity.Message;

@Mapper
public interface MessageMapper {
    MessageMapper MESSAGE_MAPPER = Mappers.getMapper(MessageMapper.class);
    MessageDto toDto(Message message);
    Message toEntity(MessageDto messageDto);
}
