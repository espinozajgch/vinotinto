package com.barrica.vinotinto.application.mapper;

import com.barrica.vinotinto.domain.model.dto.StatisticsDto;
import com.barrica.vinotinto.infrastructure.adapter.entity.MatchStatisticsDbo;
import com.barrica.vinotinto.domain.model.dto.MatchStatisticsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MatchStatisticsMapper {

    MatchStatisticsDto mapMatchStatisticsDboToDto(MatchStatisticsDbo matchStatisticsDbo);
    StatisticsDto mapMatchStatisticsDboToStatisticsDto(MatchStatisticsDbo matchStatisticsDbo);
    MatchStatisticsDbo mapMatchStatisticsDtoToDbo(MatchStatisticsDto playerMatchStatisticsDto);
}
