package com.barrica.vinotinto.infrastructure.rest.controller;

import com.barrica.vinotinto.application.service.SofaScoreService;
import com.barrica.vinotinto.domain.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SportAnalysisController {

    @Autowired
    SofaScoreService sofaScoreService;

    @GetMapping("/players")
    public ResponseEntity<List<PlayerDto>> getPlayers(){
        return ResponseEntity.ok(sofaScoreService.getPlayersList());
    }

    @GetMapping("/players/sync")
    public ResponseEntity<List<PlayerDto>> syncPlayers(){
        return ResponseEntity.ok(sofaScoreService.syncPlayers());
    }

    @GetMapping("/player/{playerId}/sync")
    public ResponseEntity<PlayerDto> syncPlayer(@PathVariable Integer playerId) {
        return ResponseEntity.ok(sofaScoreService.syncPlayer(playerId));
    }

    @GetMapping("/players/matches")
    public ResponseEntity<List<PlayerMatchDto>> getPlayerMatch(){
        return ResponseEntity.ok(sofaScoreService.getPlayerMatchList());
    }

    @GetMapping("/players/matches/sync/last")
    public ResponseEntity<Void> syncPlayersMatches(){
        sofaScoreService.syncPlayersMatches();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/players/matches/sync/next")
    public ResponseEntity<Void> syncPlayersNextMatches(){
        sofaScoreService.syncPlayersNextMatches();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/player/{playerId}/match/sync")
    public ResponseEntity<Void> syncPlayerMatch(@PathVariable Integer playerId){
        sofaScoreService.syncPlayerMatch(playerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/players/matches/statistics")
    public ResponseEntity<List<PlayerMatchStatisticsDto>> getPlayerMatchStatistics(){
        return ResponseEntity.ok(sofaScoreService.getPlayerMatchStatistics());
    }

    @GetMapping("/players/matches/statistics/sync")
    public ResponseEntity<Void> syncPlayerMatchStatistics(){
        sofaScoreService.syncPlayerMatchStatistics();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/players/matches/highlights")
    public ResponseEntity<List<PlayerMatchHighlightsDto>> gePlayerMatchHighlights() {
        return ResponseEntity.ok(sofaScoreService.getPlayerMatchHighlights());
    }

    @GetMapping("/players/matches/highlights/sync")
    public ResponseEntity<Void> syncMatchHighlights() {
        sofaScoreService.syncMatchesHighlights();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/player/match/{matchId}/highlights/sync")
    public ResponseEntity<Void> syncMatchHighlights(@PathVariable Integer matchId) {
        sofaScoreService.syncMatchHighlights(matchId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/players/matches/overall")
    public ResponseEntity<List<PlayerMatchOverallDto>> getPlayerMatchOverall() {
        return ResponseEntity.ok(sofaScoreService.getPlayerMatchOverallList());
    }

    @GetMapping("/players/matches/overall/rrss")
    public ResponseEntity<List<PlayerMatchOverallRRSSDto>> getPlayerMatchOverallRRSS() {
        return ResponseEntity.ok(sofaScoreService.getPlayerMatchOverallRRSS(false));
    }

    @GetMapping("/players/matches/overall/rrss/twitter")
    public ResponseEntity<List<PlayerMatchOverallRRSSDto>> getPlayerMatchOverallRRSSTwitter() {
        return ResponseEntity.ok(sofaScoreService.getPlayerMatchOverallRRSS(true));
    }

    @GetMapping("/matches")
    public ResponseEntity<List<MatchDto>> getMatches(){
        return ResponseEntity.ok(sofaScoreService.getMatchList());
    }

    @GetMapping("/team/sync")
    public ResponseEntity<Void> syncTeam(){
        sofaScoreService.syncTeam();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
