package org.example.datasource.storage;

import org.example.datasource.model.GameEntity;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GameStorage {

    private final Map<UUID, GameEntity> storage = new ConcurrentHashMap<>();

    public void save(GameEntity game) {
        storage.put(game.getId(), game);
    }

    public GameEntity get(UUID id) {
        return storage.get(id);
    }
}