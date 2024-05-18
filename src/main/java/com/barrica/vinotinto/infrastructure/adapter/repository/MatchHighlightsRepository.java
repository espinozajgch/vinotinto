package com.barrica.vinotinto.infrastructure.adapter.repository;

import com.barrica.vinotinto.infrastructure.adapter.entity.MatchHighlightsDbo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchHighlightsRepository extends JpaRepository<MatchHighlightsDbo, Integer> {

    List<MatchHighlightsDbo> findByMatchId(Integer matchId);
}