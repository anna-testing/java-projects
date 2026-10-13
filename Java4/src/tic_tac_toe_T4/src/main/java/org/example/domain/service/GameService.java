package org.example.domain.service;

import org.example.domain.model.Game;
import java.util.List;
import java.util.UUID;

public interface GameService {
    Game createGameWithComputer(UUID playerXId);
    Game createGameVsHuman(UUID playerXId);
    List<Game> getWaitingGames();
    Game joinGame(UUID gameId, UUID playerOId);
    Game findById(UUID gameId);
    Game makeMove(UUID gameId, UUID playerId, int row, int col);
}