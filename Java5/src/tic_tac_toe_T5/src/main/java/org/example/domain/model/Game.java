package org.example.domain.model;

import java.util.UUID;
import java.time.LocalDateTime;

public class Game {
    private UUID id;
    private GameBoard board;
    private GameStatus status;
    private UUID playerXId;
    private UUID playerOId;
    private UUID currentTurnId;
    private boolean vsComputer;
    // Timestamp when the game was created.
    private LocalDateTime creationDate;

    public Game(UUID id, GameBoard board, GameStatus status, UUID playerXId, UUID playerOId, UUID currentTurnId, boolean vsComputer, LocalDateTime creationDate) {
        this.id = id;
        this.board = board;
        this.status = status;
        this.playerXId = playerXId;
        this.playerOId = playerOId;
        this.currentTurnId = currentTurnId;
        this.vsComputer = vsComputer;
        this.creationDate = creationDate != null ? creationDate : LocalDateTime.now();
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

    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
}