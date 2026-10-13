package org.example.domain.service;

import java.util.List;
import java.util.UUID;

import org.example.domain.model.Game;

public interface GameService {
    Game createGameWithComputer(UUID playerXId);
    Game createGameVsHuman(UUID playerXId);
    List<Game> getWaitingGames();
    Game joinGame(UUID gameId, UUID playerOId);
    Game findById(UUID gameId);
    Game makeMove(UUID gameId, UUID playerId, int row, int col);

    /**
     * Return all completed games for the given user (wins or draws).
     */
    List<Game> getCompletedGamesByUserId(UUID userId);

    /**
     * Return the top N players sorted by win ratio.
     */
    List<org.example.domain.model.LeaderboardEntry> getTopPlayers(int n);

    /**
     * Return the top N players as web responses (with login).
     */
    List<org.example.web.model.LeaderboardResponse> getTopPlayersWeb(int n);
}