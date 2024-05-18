package com.barrica.vinotinto.application.mapper;

import com.barrica.vinotinto.infrastructure.adapter.entity.MatchRrssDbo;
import com.barrica.vinotinto.domain.model.dto.MatchRrssDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MatchRrssMapper {

    MatchRrssDto mapMatchRrssDboToDto(MatchRrssDbo matchRrssDbo);

    MatchRrssDbo mapMatchRrssDtoToDbo(MatchRrssDto matchRrssDto);
}
