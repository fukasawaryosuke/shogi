package com.shogi.domain.service;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Turn;
import com.shogi.domain.entity.Board;
import com.shogi.domain.entity.piece.Piece;

public class Move {
    private Board board;
    private Turn turn;

    public Move(Board board, Turn turn) {
        this.board = board;
        this.turn = turn;
    }

    public Piece movePiece(Position from, Position to) {
        Piece piece = this.board.getPiece(from);
        Piece targetPiece = this.board.getPiece(to);

        this.validateMove(from, to, piece, targetPiece);

        this.board.removePiece(from);
        this.board.putPiece(to, piece);

        return targetPiece;
    }

    private void validateMove(Position from, Position to, Piece piece, Piece targetPiece) {
        Player currentPlayer = turn.getCurrentPlayer();

        if (!board.hasPiece(from))
            throw new IllegalArgumentException(from.toString() + "に駒が存在しません");

        if (!piece.isOwner(currentPlayer))
            throw new IllegalArgumentException(currentPlayer + "の駒を選択してください");

        if (!piece.canMove(from, to))
            throw new IllegalArgumentException(piece.toString() + "は" + to.toString() + "に移動できません");

        if (!board.isPathClear(from, to))
            throw new IllegalArgumentException("移動経路に駒が存在します");

        if (board.hasPiece(to) && targetPiece.isOwner(currentPlayer))
            throw new IllegalArgumentException(to.toString() + "に自分の駒が存在します");
    }
}
