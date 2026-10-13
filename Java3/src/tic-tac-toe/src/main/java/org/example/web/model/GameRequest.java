package org.example.web.model;

public class GameRequest {

    private int[][] board;

    public GameRequest() {
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }
}