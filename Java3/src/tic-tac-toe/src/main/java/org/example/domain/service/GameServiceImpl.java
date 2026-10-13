
package org.example.domain.service.impl;

import org.example.domain.model.Game;
import org.example.domain.model.GameBoard;
import org.example.domain.service.GameService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {

    // 1 = игрок (X), 2 = компьютер (O), 0 = пусто
    private static final int PLAYER = 1;
    private static final int COMPUTER = 2;

    @Override
    public Game makeNextMove(Game game) {
        int[][] board = game.getBoard().getBoard();
        int[] bestMove = findBestMove(board);

        if (bestMove[0] != -1) {
            board[bestMove[0]][bestMove[1]] = COMPUTER;
        }
        return new Game(game.getId(), new GameBoard(board));
    }

    @Override
    public boolean validate(Game previousGame, Game currentGame) {
        if (previousGame == null || currentGame == null) return false;

        int[][] prev = previousGame.getBoard().getBoard();
        int[][] curr = currentGame.getBoard().getBoard();

        if (isGameFinished(previousGame)) return false;

        int diffCount = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (prev[i][j] != curr[i][j]) {
                    diffCount++;
                    if (!(prev[i][j] == 0 && curr[i][j] == PLAYER)) {
                        return false;
                    }
                }
            }
        }
        return diffCount == 1;
    }

    @Override
    public boolean isGameFinished(Game game) {
        int[][] board = game.getBoard().getBoard();
        return checkWinner(board, PLAYER) || checkWinner(board, COMPUTER) || isDraw(board);
    }

    @Override
    public String getWinner(Game game) {
        int[][] board = game.getBoard().getBoard();
        if (checkWinner(board, PLAYER)) return "X";
        if (checkWinner(board, COMPUTER)) return "O";
        if (isDraw(board)) return "DRAW";
        return null;
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
                    board[i][j] = COMPUTER;
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
                        board[i][j] = COMPUTER;
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
                        board[i][j] = PLAYER;
                        best = Math.min(best, minimax(board, depth + 1, true));
                        board[i][j] = 0;
                    }
                }
            }
            return best;
        }
    }

    private int evaluate(int[][] board) {
        if (checkWinner(board, COMPUTER)) return 10;
        if (checkWinner(board, PLAYER)) return -10;
        return 0;
    }
}