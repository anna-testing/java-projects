package org.example.datasource.model;

import jakarta.persistence.*;
import org.example.domain.model.GameStatus;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "games")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 9)
    private String board;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameStatus status;

    private UUID playerXId;
    private UUID playerOId;
    private UUID currentTurnId;

    private boolean vsComputer; // игра с компьютером?
    // Timestamp when the game was created.
    @Column(nullable = false)
    private LocalDateTime creationDate;

    public GameEntity() {}

    public GameEntity(UUID id, String board, GameStatus status, UUID playerXId, UUID playerOId, UUID currentTurnId, boolean vsComputer, LocalDateTime creationDate) {
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

    public String getBoard() { return board; }
    public void setBoard(String board) { this.board = board; }

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