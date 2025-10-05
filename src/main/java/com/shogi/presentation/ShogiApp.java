package com.shogi.presentation;

import com.shogi.domain.service.Board;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.entity.Piece;

public class ShogiApp {
    public static void main(String[] args) {
        // 盤面を作成
        Board board = new Board();

        // 盤面を表示
        board.display();

        // 特定の位置の駒を確認
        Position pos = new Position(1, 5);
        Piece piece = board.getPieceAt(pos);
        if (piece != null) {
            System.out.println("\n位置 " + pos + " の駒: " + piece.getDisplayName());
        }
    }
}
