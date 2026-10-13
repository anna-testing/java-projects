package org.example.web.controller;

import org.example.domain.model.Game;
import org.example.domain.service.GameService;
import org.example.web.mapper.GameWebMapper;
import org.example.web.model.GameResponse;
import org.example.web.model.MoveRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;
    private final GameWebMapper mapper;

    public GameController(GameService gameService, GameWebMapper mapper) {
        this.gameService = gameService;
        this.mapper = mapper;
    }


    @PostMapping("/vs-computer")
    public ResponseEntity<GameResponse> createVsComputer(HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("userId");
        Game game = gameService.createGameWithComputer(userId);
        return ResponseEntity.ok(mapper.toResponse(game));
    }


    @PostMapping("/vs-human")
    public ResponseEntity<GameResponse> createVsHuman(HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("userId");
        Game game = gameService.createGameVsHuman(userId);
        return ResponseEntity.ok(mapper.toResponse(game));
    }


    @GetMapping("/waiting")
    public ResponseEntity<List<GameResponse>> getWaitingGames() {
        List<Game> games = gameService.getWaitingGames();
        List<GameResponse> responses = games.stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/{gameId}/join")
    public ResponseEntity<GameResponse> joinGame(@PathVariable UUID gameId, HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("userId");
        Game game = gameService.joinGame(gameId, userId);
        return ResponseEntity.ok(mapper.toResponse(game));
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameResponse> getGame(@PathVariable UUID gameId) {
        Game game = gameService.findById(gameId);
        return ResponseEntity.ok(mapper.toResponse(game));
    }

    @PostMapping("/{gameId}/move")
    public ResponseEntity<GameResponse> makeMove(@PathVariable UUID gameId,
                                                 @RequestBody MoveRequest move,
                                                 HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("userId");
        Game updated = gameService.makeMove(gameId, userId, move.getRow(), move.getCol());
        return ResponseEntity.ok(mapper.toResponse(updated));
    }
}