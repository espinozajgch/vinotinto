package com.barrica.vinotinto.domain.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchStatisticsDto {

    private Integer playerId;
    private Integer matchId;
    String measure;
    String value;
}
