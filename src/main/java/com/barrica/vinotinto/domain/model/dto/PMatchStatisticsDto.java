package com.barrica.vinotinto.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PMatchStatisticsDto {

    @JsonProperty("match")
    MatchDto matchDto;

    @JsonProperty("statistics")
    List<StatisticsDto> statisticsDtoList;
}
