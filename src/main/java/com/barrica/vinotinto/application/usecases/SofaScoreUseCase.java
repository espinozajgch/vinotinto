package com.barrica.vinotinto.application.usecases;

import com.barrica.vinotinto.domain.model.dto.*;

import java.util.List;

public interface SofaScoreUseCase {

    List<PlayerDto> getPlayersList();

    List<MatchDto> getMatchList();

    List<MatchDto> getMatchListByPlayer(int playerId);

    List<StatisticsDto> getMatchStatisticsListByMatchIdAndPlayerId(Integer matchId, Integer playerId);

    List<PMatchStatisticsDto> getMatchStatisticsListByPlayer(int playerId);

    List<PMatchHighlightsDto> getMatchHighlightsByPlayerId(Integer playerId);

    List<PMatchOverallDto> getMatchOverallDtoList(int playerId, List<MatchDto> matchDtoList);

    void syncPlayerMatchStatistics();

    List<PlayerDto> syncPlayers();

    PlayerDto syncPlayer(Integer playerId);

    void syncPlayersMatches();

    void syncPlayerMatch(Integer playerId);

    void syncMatchHighlights(Integer matchId);
    void syncMatchesHighlights();
}
