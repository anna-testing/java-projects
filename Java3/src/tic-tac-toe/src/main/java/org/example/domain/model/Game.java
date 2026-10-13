package org.example.domain.model;

import java.util.UUID;

public class Game {

    private UUID id;
    private GameBoard board;

    public Game(UUID id, GameBoard board) {
        this.id = id;
        this.board = board;
    }

    public UUID getId() {
        return id;
    }

    public GameBoard getBoard() {
        return board;
    }

    public void setBoard(GameBoard board) {
        this.board = board;
    }
}