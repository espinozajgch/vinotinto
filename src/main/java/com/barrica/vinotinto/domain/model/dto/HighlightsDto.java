package com.barrica.vinotinto.domain.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HighlightsDto {

    String url;
    String thumbnailUrl;
    String title;
    String subtitle;

}
