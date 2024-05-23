package com.barrica.vinotinto.domain.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MatchDto {
    int id;
    Integer matchIdSofaScore;
    String homeTeam;
    Integer homeScore;
    String awayTeam;
    Integer awayScore;
    Integer tournamentIdSofaScore;
    String tournamentName;
    String tournamentStage;
    Integer seasonIdSofaScore;
    String seasonName;
    String seasonYear;
    String matchDate;
    Boolean hasGlobalHighlights;
    String tournamentCountry;
    String alfa2;
    String alfa3;
    String homeTeamShortName;
    String awayTeamShortName;
    String matchCity;
    String matchStadium;
    String matchCountry;
    String matchReferee;
    String matchRefereeCountry;
    Integer hometeamIdSofaScore;
    Integer awayteamIdSofaScore;
    Boolean published;
    String matchTimestamp;
    Integer status;
    Integer homeOvertimeScore;
    Integer awayOvertimeScore;
    Integer homePenaltyScore;
    Integer awayPenaltyScore;
}
