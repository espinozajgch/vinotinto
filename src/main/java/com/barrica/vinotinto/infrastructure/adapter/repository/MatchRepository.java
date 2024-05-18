package com.barrica.vinotinto.infrastructure.adapter.repository;

import com.barrica.vinotinto.infrastructure.adapter.entity.MatchDbo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchRepository extends JpaRepository<MatchDbo, Integer> {

    @Query("select m from MatchDbo m inner join PlayerMatchDbo pm on m.matchIdSofaScore = pm.matchId where pm.playerId = :playerId")
    List<MatchDbo> findMatchesByPlayerId(Integer playerId);

    MatchDbo findByMatchIdSofaScore(Integer matchIdSofaScore);

    @Query("select m from MatchDbo m inner join PlayerMatchDbo pm on m.matchIdSofaScore = pm.matchId where pm.playerId = :playerId and m.published = false")
    List<MatchDbo> findMatchesByPlayerIdAndByPublishedFalse(int playerId);

    @Query("select m from MatchDbo m inner join PlayerMatchDbo pm on m.matchIdSofaScore = pm.matchId where pm.playerId = :playerId and m.published = false and m.status = :status")
    List<MatchDbo> findMatchesByPlayerIdAndByPublishedFalseAndStatus(int playerId, int status);

    @Query("SELECT md " +
            "FROM PlayerDbo p " +
            "INNER JOIN PlayerMatchDbo pm on p.playerIdSofaScore = pm.playerId " +
            "INNER JOIN MatchDbo md on pm.matchId = md.matchIdSofaScore " +
            "WHERE md.status = 0 " +
            "AND p.playerIdSofaScore = :playerId " +
            "AND md.matchDate = :matchDate")
    MatchDbo findPlayerMatchesByDateAndStatus(Integer playerId, String matchDate);

    @Query("SELECT md " +
            "FROM PlayerDbo p " +
            "INNER JOIN PlayerMatchDbo pm on p.playerIdSofaScore = pm.playerId " +
            "INNER JOIN MatchDbo md on pm.matchId = md.matchIdSofaScore " +
            "WHERE p.playerIdSofaScore = :playerId " +
            "AND md.published = false " +
            "AND md.matchDate = :matchDate")
    MatchDbo findPlayerMatchesByDateAndByPublishedFalse(Integer playerId, String matchDate);/**/

}