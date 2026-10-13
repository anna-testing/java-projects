package org.example.datasource.model;

import java.util.UUID;

public class GameEntity {

    private UUID id;
    private GameBoardEntity board;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public GameBoardEntity getBoard() {
        return board;
    }

    public void setBoard(GameBoardEntity board) {
        this.board = board;
    }
}