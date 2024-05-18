package com.barrica.vinotinto.application.service;

import com.barrica.vinotinto.application.usecases.SofaScoreUseCaseImpl;
import com.barrica.vinotinto.domain.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SofaScoreService {

    @Autowired
    SofaScoreUseCaseImpl sofaScoreComponent;

    public List<PlayerDto> getPlayersList(){
        return sofaScoreComponent.getPlayersList();
    }

    public List<PlayerDto> syncPlayers() {
        return sofaScoreComponent.syncPlayers();
    }

    public PlayerDto syncPlayer(Integer playerId) {
        return sofaScoreComponent.syncPlayer(playerId);
    }

    public List<PlayerMatchDto> getPlayerMatchList(){
        return sofaScoreComponent.getPlayersList()
                .stream()
                .map(playerDto -> PlayerMatchDto.builder()
                        .player(playerDto)
                        .matchDtoList(sofaScoreComponent.getMatchListByPlayer(playerDto.getPlayerIdSofaScore()))
                        .build())
                .toList();
    }

    public void syncPlayersMatches(){
        sofaScoreComponent.syncPlayersMatches();
    }

    public void syncPlayersNextMatches(){
        sofaScoreComponent.syncPlayersNextMatches();
    }

    public void syncPlayerMatch(Integer playerId) {
        sofaScoreComponent.syncPlayerMatch(playerId);
    }

    public List<PlayerMatchStatisticsDto> getPlayerMatchStatistics() {
        return sofaScoreComponent.getPlayersList().stream()
                .map(playerDto -> PlayerMatchStatisticsDto.builder()
                        .player(playerDto)
                        .pMatchStatisticsDtoList(sofaScoreComponent.getMatchStatisticsListByPlayer(playerDto.getPlayerIdSofaScore()))
                        .build())
                .toList();
    }

    public void syncPlayerMatchStatistics() {
        sofaScoreComponent.syncPlayerMatchStatistics();
    }

    public List<PlayerMatchHighlightsDto> getPlayerMatchHighlights() {
        return sofaScoreComponent.getPlayersList().stream()
                .map(playerDto -> PlayerMatchHighlightsDto.builder()
                        .player(playerDto)
                        .pMatchHighlightsDtoList(sofaScoreComponent.getMatchHighlightsByPlayerId(playerDto.getPlayerIdSofaScore()))
                        .build())
                .toList();
    }

    public void syncMatchesHighlights() {
        sofaScoreComponent.syncMatchesHighlights();
    }

    public void syncMatchHighlights(Integer matchId) {
        sofaScoreComponent.syncMatchHighlights(matchId);
    }

    public List<MatchDto> getMatchList(){
        return sofaScoreComponent.getMatchList();
    }

    public List<PlayerMatchOverallDto> getPlayerMatchOverallList() {
        return sofaScoreComponent.getPlayersHasMatchList().stream()
                .map(playerDto -> {
                    List<MatchDto> matchDtoList = sofaScoreComponent.getMatchListByPlayer(playerDto.getPlayerIdSofaScore());

                    return PlayerMatchOverallDto
                        .builder()
                        .player(playerDto)
                        .pMatchOverallDtoList(sofaScoreComponent.getMatchOverallDtoList(playerDto.getPlayerIdSofaScore(), matchDtoList))
                        .build();
                })
                .toList();

    }

    public List<PlayerMatchOverallRRSSDto> getPlayerMatchOverallRRSS(boolean tweet) {
        return sofaScoreComponent.getPlayersHasMatchList().stream()
                .map(playerDto -> {
                    List<MatchDto> matchDtoList = sofaScoreComponent.getMatchListNotPublishedByPlayerAndByStatus(playerDto.getPlayerIdSofaScore(), 100);

                    return PlayerMatchOverallRRSSDto
                            .builder()
                            .player(playerDto)
                            .pMatchOverallRRSSDtoList(sofaScoreComponent.getMatchOverallRRSSDtoList(playerDto, matchDtoList, tweet))
                            .build();
                })
                .toList();
    }

    public void syncTeam() {
        sofaScoreComponent.syncTeam();
    }


}
