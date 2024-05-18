package com.barrica.vinotinto.domain.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MatchRrssDto {

    int id;
    Integer matchIdSofaScore;
    String tweetId;
    String message;

}
