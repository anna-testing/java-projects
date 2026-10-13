// org/example/web/mapper/GameWebMapper.java
package org.example.web.mapper;

import org.example.domain.model.Game;
import org.example.domain.model.GameBoard;
import org.example.domain.service.GameService;
import org.example.web.model.GameRequest;
import org.example.web.model.GameResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GameWebMapper {

    private final GameService gameService;

    public GameWebMapper(GameService gameService) {
        this.gameService = gameService;
    }

    public Game toDomain(UUID id, GameRequest request) {
        if (request == null) return null;
        return new Game(id, new GameBoard(request.getBoard()));
    }

    public GameResponse toResponse(Game game) {
        if (game == null) return null;

        GameResponse response = new GameResponse();
        response.setId(game.getId());
        response.setBoard(game.getBoard().getBoard());
        response.setGameOver(gameService.isGameFinished(game));
        response.setWinner(gameService.getWinner(game));
        return response;
    }
}