package com.barrica.vinotinto.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PMatchHighlightsDto {

    @JsonProperty("match")
    MatchDto matchDto;

    @JsonProperty("highlights")
    List<HighlightsDto> highlightsDtoList;
}
