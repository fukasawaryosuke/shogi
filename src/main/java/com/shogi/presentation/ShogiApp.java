package com.shogi.presentation;

import com.shogi.domain.entity.Board;

public class ShogiApp {
    public static void main(String[] args) {
        Board board = new Board();
        board.display();
    }
}
