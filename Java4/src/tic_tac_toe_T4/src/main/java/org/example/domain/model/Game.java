package org.example.domain.model;

import java.util.UUID;

public class Game {
    private UUID id;
    private GameBoard board;
    private GameStatus status;
    private UUID playerXId;
    private UUID playerOId;
    private UUID currentTurnId;
    private boolean vsComputer;

    public Game(UUID id, GameBoard board, GameStatus status, UUID playerXId, UUID playerOId, UUID currentTurnId, boolean vsComputer) {
        this.id = id;
        this.board = board;
        this.status = status;
        this.playerXId = playerXId;
        this.playerOId = playerOId;
        this.currentTurnId = currentTurnId;
        this.vsComputer = vsComputer;
    }


    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public GameBoard getBoard() { return board; }
    public void setBoard(GameBoard board) { this.board = board; }

    public GameStatus getStatus() { return status; }
    public void setStatus(GameStatus status) { this.status = status; }

    public UUID getPlayerXId() { return playerXId; }
    public void setPlayerXId(UUID playerXId) { this.playerXId = playerXId; }

    public UUID getPlayerOId() { return playerOId; }
    public void setPlayerOId(UUID playerOId) { this.playerOId = playerOId; }

    public UUID getCurrentTurnId() { return currentTurnId; }
    public void setCurrentTurnId(UUID currentTurnId) { this.currentTurnId = currentTurnId; }

    public boolean isVsComputer() { return vsComputer; }
    public void setVsComputer(boolean vsComputer) { this.vsComputer = vsComputer; }
}