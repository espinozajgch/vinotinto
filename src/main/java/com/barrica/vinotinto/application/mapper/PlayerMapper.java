package com.barrica.vinotinto.application.mapper;

import com.barrica.vinotinto.domain.model.dto.PlayerDto;
import com.barrica.vinotinto.infrastructure.adapter.entity.PlayerDbo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    PlayerDto mapPlayerDboToDto(PlayerDbo playerDbo);

    PlayerDbo mapPlayerDtoToDbo(PlayerDto playerDto);
}
