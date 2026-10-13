package org.example.web.mapper;

import org.example.domain.model.Game;
import org.example.domain.model.GameStatus;
import org.example.web.model.GameResponse;
import org.springframework.stereotype.Component;

@Component
public class GameWebMapper {

    public GameResponse toResponse(Game game) {
        if (game == null) return null;
        GameResponse response = new GameResponse();
        response.setId(game.getId());
        response.setBoard(game.getBoard().getBoard());
        response.setGameOver(game.getStatus() != GameStatus.IN_PROGRESS && game.getStatus() != GameStatus.WAITING_FOR_PLAYER);

        if (game.getStatus() == GameStatus.WIN_X) response.setWinner("X");
        else if (game.getStatus() == GameStatus.WIN_O) response.setWinner("O");
        else if (game.getStatus() == GameStatus.DRAW) response.setWinner("DRAW");
        else response.setWinner(null);
        return response;
    }
}