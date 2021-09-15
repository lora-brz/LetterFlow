package ru.example.letterflow.service.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.example.letterflow.domain.dto.RoomDto;
import ru.example.letterflow.domain.entity.Room;

@Mapper
public interface RoomMapper {
    RoomMapper ROOM_MAPPER = Mappers.getMapper(RoomMapper.class);
    RoomDto toDto(Room room);
    Room toEntity(RoomDto roomDto);
}
