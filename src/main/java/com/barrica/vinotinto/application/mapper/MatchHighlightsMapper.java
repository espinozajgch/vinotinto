package com.barrica.vinotinto.application.mapper;

import com.barrica.vinotinto.infrastructure.adapter.entity.MatchHighlightsDbo;
import com.barrica.vinotinto.domain.model.dto.MatchHighlightsDto;
import com.barrica.vinotinto.domain.model.dto.HighlightsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MatchHighlightsMapper {

    MatchHighlightsDto mapMatchHighlightsDboToDto(MatchHighlightsDbo matchHighlightsDbo);
    MatchHighlightsDbo mapMatchHighlightsDtoToDbo(MatchHighlightsDto matchHighlightsDto);
    HighlightsDto mapMatchStatisticsDboToHighlightsDto(MatchHighlightsDbo matchHighlightsDbo);
}
