package com.barrica.vinotinto.domain.model.dto;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class PlayerDto {
    String playerName;
    int playerIdSofaScore;
    String playerPosition;
    int playerHeight;
    int playerNumber;
    String playerPreferredFoot;
    String playerDateOfBirth;
    String playerGender;
    String team;
    int teamIdSofaScore;
    String teamCountry;
    String twitter;
    String ig;
    String teamTwitter;
    Boolean hasMatch;
}
