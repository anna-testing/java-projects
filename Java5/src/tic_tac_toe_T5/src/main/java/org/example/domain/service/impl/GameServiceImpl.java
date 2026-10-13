package org.example.domain.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.example.datasource.mapper.GameDataMapper;
import org.example.datasource.repository.GameJpaRepository;
import org.example.domain.model.Game;
import org.example.domain.model.GameBoard;
import org.example.domain.model.GameStatus;
import org.example.domain.model.LeaderboardEntry;
import org.example.domain.service.GameService;
import org.example.domain.service.UserService;
import org.example.web.model.LeaderboardResponse;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    private static final int EMPTY = 0;
    private static final int PLAYER_X = 1;
    private static final int PLAYER_O = 2;

    private final GameJpaRepository repository;
    private final GameDataMapper mapper;
    private final UserService userService;

    public GameServiceImpl(GameJpaRepository repository, GameDataMapper mapper, UserService userService) {
        this.repository = repository;
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    public Game createGameWithComputer(UUID playerXId) {
        UUID gameId = UUID.randomUUID();
        String emptyBoard = "000000000";
        Game game = new Game(
                gameId,
                new GameBoard(stringToMatrix(emptyBoard)),
                GameStatus.IN_PROGRESS,
                playerXId,
                null,                     // второй игрок пока не присоединился
                playerXId,                // пока ход за X (но игра не начата)
                true,                    // vsComputer = true
                java.time.LocalDateTime.now()
        );
        return mapper.toDomain(repository.save(mapper.toEntity(game)));
    }

    @Override
    public Game createGameVsHuman(UUID playerXId) {
        UUID gameId = UUID.randomUUID();
        String emptyBoard = "000000000";
        Game game = new Game(
                gameId,
                new GameBoard(stringToMatrix(emptyBoard)),
                GameStatus.WAITING_FOR_PLAYER,
                playerXId,
                null,                     // второй игрок пока не присоединился
                playerXId,                // пока ход за X (но игра не начата)
                false,                    // vsComputer = false
                java.time.LocalDateTime.now()
        );
        return mapper.toDomain(repository.save(mapper.toEntity(game)));
    }

    @Override
    public List<Game> getWaitingGames() {
        return repository.findByStatus(GameStatus.WAITING_FOR_PLAYER)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Game joinGame(UUID gameId, UUID playerOId) {
        Game game = findById(gameId);
        if (game.getStatus() != GameStatus.WAITING_FOR_PLAYER) {
            throw new IllegalStateException("Game is not waiting for player");
        }
        if (game.getPlayerXId().equals(playerOId)) {
            throw new IllegalArgumentException("You cannot join your own game");
        }
        game.setPlayerOId(playerOId);
        game.setStatus(GameStatus.IN_PROGRESS);

        return mapper.toDomain(repository.save(mapper.toEntity(game)));
    }

    @Override
    public Game findById(UUID gameId) {
        return mapper.toDomain(repository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found")));
    }

    @Override
    public List<Game> getCompletedGamesByUserId(UUID userId) {
        return repository.findCompletedGamesByUserId(userId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<LeaderboardEntry> getTopPlayers(int n) {
        return repository.findTopPlayers().stream()
                .limit(n)
                .collect(Collectors.toList());
    }

    @Override
    public List<LeaderboardResponse> getTopPlayersWeb(int n) {
        return repository.findTopPlayers().stream()
                .limit(n)
                .map(entry -> {
                    String login = userService.findById(entry.getUserId())
                            .map(user -> user.getLogin())
                            .orElse("Unknown");
                    return new LeaderboardResponse(entry.getUserId(), login, entry.getWinRatio());
                })
                .collect(Collectors.toList());
    }

    @Override
    public Game makeMove(UUID gameId, UUID playerId, int row, int col) {
        Game game = findById(gameId);


        if (game.getStatus() != GameStatus.IN_PROGRESS) {
            throw new IllegalStateException("Game is not in progress");
        }


        if (!game.getCurrentTurnId().equals(playerId)) {
            throw new IllegalStateException("Not your turn");
        }


        int playerSymbol;
        if (game.getPlayerXId().equals(playerId)) {
            playerSymbol = PLAYER_X;
        } else if (game.getPlayerOId() != null && game.getPlayerOId().equals(playerId)) {
            playerSymbol = PLAYER_O;
        } else {
            throw new IllegalArgumentException("Player not part of this game");
        }


        int[][] board = game.getBoard().getBoard();
        if (row < 0 || row > 2 || col < 0 || col > 2 || board[row][col] != EMPTY) {
            throw new IllegalArgumentException("Invalid move");
        }


        board[row][col] = playerSymbol;
        game.getBoard().setBoard(board);


        if (checkWinner(board, playerSymbol)) {
            if (playerSymbol == PLAYER_X) {
                game.setStatus(GameStatus.WIN_X);
            } else {
                game.setStatus(GameStatus.WIN_O);
            }

            return mapper.toDomain(repository.save(mapper.toEntity(game)));
        }

        if (isDraw(board)) {
            game.setStatus(GameStatus.DRAW);
            return mapper.toDomain(repository.save(mapper.toEntity(game)));
        }


        if (game.isVsComputer() && playerSymbol == PLAYER_X) {

            repository.save(mapper.toEntity(game));

            return makeComputerMove(gameId);
        } else {

            if (game.getPlayerXId().equals(playerId)) {
                game.setCurrentTurnId(game.getPlayerOId());
            } else {
                game.setCurrentTurnId(game.getPlayerXId());
            }
            return mapper.toDomain(repository.save(mapper.toEntity(game)));
        }
    }

    private Game makeComputerMove(UUID gameId) {
        Game game = findById(gameId);
        if (game.getStatus() != GameStatus.IN_PROGRESS) return game;

        int[][] board = game.getBoard().getBoard();
        int[] bestMove = findBestMove(board); // компьютер = PLAYER_O (2)

        if (bestMove[0] != -1) {
            board[bestMove[0]][bestMove[1]] = PLAYER_O;
            game.getBoard().setBoard(board);
        }

        // Проверка победы компьютера или ничьи
        if (checkWinner(board, PLAYER_O)) {
            game.setStatus(GameStatus.WIN_O);
        } else if (isDraw(board)) {
            game.setStatus(GameStatus.DRAW);
        } else {
            // Ход переходит обратно к игроку
            game.setCurrentTurnId(game.getPlayerXId());
        }

        return mapper.toDomain(repository.save(mapper.toEntity(game)));
    }

    private boolean checkWinner(int[][] board, int player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;
        return false;
    }

    private boolean isDraw(int[][] board) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == 0) return false;
        return true;
    }

    private int[] findBestMove(int[][] board) {
        int bestVal = Integer.MIN_VALUE;
        int[] bestMove = new int[]{-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = PLAYER_O; // компьютер
                    int moveVal = minimax(board, 0, false);
                    board[i][j] = 0;

                    if (moveVal > bestVal) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimax(int[][] board, int depth, boolean isMaximizing) {
        int score = evaluate(board);
        if (Math.abs(score) == 10) return score;
        if (isDraw(board)) return 0;

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = PLAYER_O;
                        best = Math.max(best, minimax(board, depth + 1, false));
                        board[i][j] = 0;
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = PLAYER_X;
                        best = Math.min(best, minimax(board, depth + 1, true));
                        board[i][j] = 0;
                    }
                }
            }
            return best;
        }
    }

    private int evaluate(int[][] board) {
        if (checkWinner(board, PLAYER_O)) return 10;
        if (checkWinner(board, PLAYER_X)) return -10;
        return 0;
    }

    private int[][] stringToMatrix(String s) {
        int[][] matrix = new int[3][3];
        for (int i = 0; i < 9; i++) {
            matrix[i / 3][i % 3] = Character.getNumericValue(s.charAt(i));
        }
        return matrix;
    }
}