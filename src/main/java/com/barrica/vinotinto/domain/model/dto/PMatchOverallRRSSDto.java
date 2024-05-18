package com.barrica.vinotinto.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PMatchOverallRRSSDto {

    @JsonProperty("match")
    MatchDto matchDto;

    @JsonProperty("rrss")
    String rrss;

}
