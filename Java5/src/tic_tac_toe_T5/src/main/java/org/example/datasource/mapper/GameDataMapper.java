package org.example.datasource.mapper;

import org.example.datasource.model.GameEntity;
import org.example.domain.model.Game;
import org.example.domain.model.GameBoard;
import org.springframework.stereotype.Component;

@Component
public class GameDataMapper {

    public Game toDomain(GameEntity entity) {
        if (entity == null) return null;
        int[][] boardMatrix = stringToMatrix(entity.getBoard());
        GameBoard board = new GameBoard(boardMatrix);
        return new Game(
                entity.getId(),
                board,
                entity.getStatus(),
                entity.getPlayerXId(),
                entity.getPlayerOId(),
                entity.getCurrentTurnId(),
                entity.isVsComputer(),
                entity.getCreationDate()
        );
    }

    public GameEntity toEntity(Game domain) {
        if (domain == null) return null;
        String boardString = matrixToString(domain.getBoard().getBoard());
        return new GameEntity(
                domain.getId(),
                boardString,
                domain.getStatus(),
                domain.getPlayerXId(),
                domain.getPlayerOId(),
                domain.getCurrentTurnId(),
                domain.isVsComputer(),
                domain.getCreationDate()
        );
    }

    private String matrixToString(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(matrix[i][j]);
            }
        }
        return sb.toString();
    }

    private int[][] stringToMatrix(String s) {
        int[][] matrix = new int[3][3];
        for (int i = 0; i < 9; i++) {
            char c = s.charAt(i);
            matrix[i / 3][i % 3] = Character.getNumericValue(c);
        }
        return matrix;
    }
}