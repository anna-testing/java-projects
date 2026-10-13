package org.example.domain.model;

import java.util.UUID;

/**
 * Simple DTO representing a player and their win ratio.
 */
public class LeaderboardEntry {
    private UUID userId;
    private double winRatio;

    public LeaderboardEntry() {}

    public LeaderboardEntry(UUID userId, double winRatio) {
        this.userId = userId;
        this.winRatio = winRatio;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public double getWinRatio() {
        return winRatio;
    }

    public void setWinRatio(double winRatio) {
        this.winRatio = winRatio;
    }
}
