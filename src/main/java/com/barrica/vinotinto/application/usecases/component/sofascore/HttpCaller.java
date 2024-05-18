package com.barrica.vinotinto.application.usecases.component.sofascore;

import com.barrica.vinotinto.domain.model.dto.MatchDto;
import com.barrica.vinotinto.domain.model.dto.MatchHighlightsDto;
import com.barrica.vinotinto.domain.model.dto.MatchStatisticsDto;
import com.barrica.vinotinto.domain.model.dto.PlayerDto;

import java.util.List;

public interface HttpCaller {

    PlayerDto getPlayersByPlayerId(int playerId);
    List<PlayerDto>  getPlayersListByTeamId(int teamId);
    MatchDto getLastMatchIdByPlayer(int playerId);
    void getMatchDataById(int eventId);
    List<MatchStatisticsDto> getMatchStatisticsByPlayer(int eventId, int playerId);
    List<MatchHighlightsDto> getMatchHighlights(int eventId);
}
