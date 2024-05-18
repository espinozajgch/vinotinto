package com.barrica.vinotinto.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PlayerMatchOverallRRSSDto {

    PlayerDto player;

    @JsonProperty("matches")
    List<PMatchOverallRRSSDto> pMatchOverallRRSSDtoList;

}
