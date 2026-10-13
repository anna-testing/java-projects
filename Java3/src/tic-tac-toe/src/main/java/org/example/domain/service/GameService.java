
package org.example.domain.service;

import org.example.domain.model.Game;

public interface GameService {

    Game makeNextMove(Game game);

    boolean validate(Game previousGame, Game currentGame);

    boolean isGameFinished(Game game);

    String getWinner(Game game);
}