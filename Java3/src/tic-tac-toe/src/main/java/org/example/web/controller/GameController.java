package org.example.web.controller;

import org.example.domain.model.Game;
import org.example.domain.model.GameBoard;
import org.example.domain.service.GameService;
import org.example.datasource.repository.GameRepository;
import org.example.web.mapper.GameWebMapper;
import org.example.web.model.GameRequest;
import org.example.web.model.GameResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;
    private final GameRepository repository;
    private final GameWebMapper mapper;

    public GameController(GameService gameService,
                          GameRepository repository,
                          GameWebMapper mapper) {
        this.gameService = gameService;
        this.repository = repository;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<GameResponse> createNewGame() {
        UUID newId = UUID.randomUUID();
        int[][] emptyBoard = new int[3][3];
        Game newGame = new Game(newId, new GameBoard(emptyBoard));
        repository.save(newGame);
        GameResponse response = mapper.toResponse(newGame);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> makeMove(@PathVariable UUID id,
                                      @RequestBody GameRequest request) {

        Game currentGame = mapper.toDomain(id, request);
        Game previousGame = repository.findById(id);

        if (previousGame == null) {
            int[][] emptyBoard = new int[3][3];
            previousGame = new Game(id, new GameBoard(emptyBoard));
            repository.save(previousGame);
        }

        if (!gameService.validate(previousGame, currentGame)) {
            return ResponseEntity.badRequest().body("Invalid move: game state was modified incorrectly");
        }

        Game updated = gameService.makeNextMove(currentGame);
        repository.save(updated);

        GameResponse response = mapper.toResponse(updated);
        return ResponseEntity.ok(response);
    }
}