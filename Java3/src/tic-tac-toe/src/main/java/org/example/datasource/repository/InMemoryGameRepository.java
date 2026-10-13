
package org.example.datasource.repository;

import org.example.domain.model.Game;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryGameRepository implements GameRepository {

    private final ConcurrentHashMap<UUID, Game> storage = new ConcurrentHashMap<>();

    @Override
    public void save(Game game) {
        storage.put(game.getId(), game);
    }

    @Override
    public Game findById(UUID id) {
        return storage.get(id);
    }
}