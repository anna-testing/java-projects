package org.example.domain.model;

public class GameBoard {

    private int[][] board;

    public GameBoard(int[][] board) {
        this.board = board;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }
}

//0 — пусто//
//1 — игрок//
//2 — компьютер//