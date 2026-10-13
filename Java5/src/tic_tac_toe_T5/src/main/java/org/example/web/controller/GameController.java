package org.example.web.controller;

import java.util.List;
import java.util.UUID;

import org.example.domain.model.Game;
import org.example.domain.service.GameService;
import org.example.security.JwtAuthentication;
import org.example.web.mapper.GameWebMapper;
import org.example.web.model.GameResponse;
import org.example.web.model.MoveRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;
    private final GameWebMapper mapper;

    public GameController(GameService gameService, GameWebMapper mapper) {
        this.gameService = gameService;
        this.mapper = mapper;
    }

    /**
     * Helper method to extract the current user's UUID from SecurityContextHolder.
     */
    private UUID getCurrentUserId() {
        JwtAuthentication auth = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UUID) {
            return (UUID) auth.getPrincipal();
        }
        throw new RuntimeException("Not authenticated");
    }


    @PostMapping("/vs-computer")
    public ResponseEntity<GameResponse> createVsComputer() {
        UUID userId = getCurrentUserId();
        Game game = gameService.createGameWithComputer(userId);
        return ResponseEntity.ok(mapper.toResponse(game));
    }


    @PostMapping("/vs-human")
    public ResponseEntity<GameResponse> createVsHuman() {
        UUID userId = getCurrentUserId();
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
    public ResponseEntity<GameResponse> joinGame(@PathVariable UUID gameId) {
        UUID userId = getCurrentUserId();
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
                                                  @RequestBody MoveRequest move) {
        UUID userId = getCurrentUserId();
        Game updated = gameService.makeMove(gameId, userId, move.getRow(), move.getCol());
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @GetMapping("/history")
    public ResponseEntity<List<GameResponse>> getHistory() {
        UUID userId = getCurrentUserId();
        List<Game> games = gameService.getCompletedGamesByUserId(userId);
        List<GameResponse> responses = games.stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/leaderboard/{n}")
    public ResponseEntity<List<org.example.web.model.LeaderboardResponse>> getLeaderboard(@PathVariable int n) {
        return ResponseEntity.ok(gameService.getTopPlayersWeb(n));
    }
}