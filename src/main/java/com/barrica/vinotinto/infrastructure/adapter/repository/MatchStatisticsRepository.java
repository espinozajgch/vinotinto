package com.barrica.vinotinto.infrastructure.adapter.repository;

import com.barrica.vinotinto.infrastructure.adapter.entity.MatchStatisticsDbo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchStatisticsRepository extends JpaRepository<MatchStatisticsDbo, Integer> {

    @Query("select m from MatchStatisticsDbo m where m.matchId = :matchId and m.playerId = :playerId ORDER BY m.measure ASC ")
    List<MatchStatisticsDbo> findStatisticsByMatchIdAndPlayerId(Integer matchId, Integer playerId);

}