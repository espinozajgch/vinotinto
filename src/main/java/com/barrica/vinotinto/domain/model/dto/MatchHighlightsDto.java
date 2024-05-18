package com.barrica.vinotinto.domain.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MatchHighlightsDto {

    Integer id;
    Integer matchId;
    String url;
    String thumbnailUrl;
    String title;
    String subtitle;

}
