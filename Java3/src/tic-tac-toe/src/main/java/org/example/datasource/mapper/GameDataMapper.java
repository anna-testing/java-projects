package org.example.datasource.mapper;

import org.example.datasource.model.GameBoardEntity;
import org.example.datasource.model.GameEntity;
import org.example.domain.model.Game;
import org.example.domain.model.GameBoard;

public class GameDataMapper {

    public Game toDomain(GameEntity entity) {
        if (entity == null) return null;

        int[][] boardCopy = copyBoard(entity.getBoard().getBoard());

        GameBoard board = new GameBoard(boardCopy);
        return new Game(entity.getId(), board);
    }

    public GameEntity toEntity(Game game) {
        if (game == null) return null;

        int[][] boardCopy = copyBoard(game.getBoard().getBoard());

        GameBoardEntity boardEntity = new GameBoardEntity();
        boardEntity.setBoard(boardCopy);

        GameEntity entity = new GameEntity();
        entity.setId(game.getId());
        entity.setBoard(boardEntity);

        return entity;
    }

    private int[][] copyBoard(int[][] original) {
        if (original == null) return null;

        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }
}