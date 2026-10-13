package org.example.datasource.repository;

import java.util.List;
import java.util.UUID;

import org.example.datasource.model.GameEntity;
import org.example.domain.model.GameStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameJpaRepository extends CrudRepository<GameEntity, UUID> {
    List<GameEntity> findByStatus(GameStatus status);

    @Query("SELECT g FROM GameEntity g WHERE (g.playerXId = :userId AND g.status = 'WIN_X') " +
           "OR (g.playerOId = :userId AND g.status = 'WIN_O') " +
           "OR (g.status = 'DRAW' AND (g.playerXId = :userId OR g.playerOId = :userId))")
    List<GameEntity> findCompletedGamesByUserId(@Param("userId") UUID userId);

    @Query("SELECT new org.example.domain.model.LeaderboardEntry(g.playerXId, " +
           "SUM(CASE WHEN g.status = 'WIN_X' THEN 1 ELSE 0 END) * 1.0 / " +
           "NULLIF(COUNT(*), 0)) " +
           "FROM GameEntity g WHERE g.playerXId IS NOT NULL GROUP BY g.playerXId ORDER BY " +
           "SUM(CASE WHEN g.status = 'WIN_X' THEN 1 ELSE 0 END) * 1.0 / " +
           "NULLIF(COUNT(*), 0) DESC")
    List<org.example.domain.model.LeaderboardEntry> findTopPlayers();
}