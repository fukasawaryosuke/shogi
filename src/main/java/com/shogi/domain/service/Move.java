package com.shogi.domain.service;

import com.shogi.domain.entity.Board;
import com.shogi.domain.entity.Stand;
import com.shogi.domain.entity.piece.Piece;
import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Turn;

public class Move {
    private Board board;
    private Stand stand;
    private Turn turn;

    public Move(Board board, Stand stand, Turn turn) {
        this.board = board;
        this.stand = stand;
        this.turn = turn;
    }

    public void movePiece(Position from, Position to, Player player) {
        Piece piece = this.board.getPiece(from);
        Piece targetPiece = this.board.getPiece(to);

        this.validateMove(piece, targetPiece, from, to, player);

        if (this.hasPiece(to)) {
            this.stand.addPiece(targetPiece);
        }

        this.board.removePiece(from);
        this.board.putPiece(to, piece);

        this.turn.next();
    }

    private void validateMove(Piece piece, Piece targetPiece, Position from, Position to, Player player) {
        if (!this.hasPiece(from))
            throw new IllegalArgumentException(from.toString() + "に駒が存在しません");

        if (!this.isOwner(piece, player))
            throw new IllegalArgumentException(player + "の駒を選択してください");

        if (!this.canMove(piece, from, to))
            throw new IllegalArgumentException(piece.toString() + "は" + to.toString() + "に移動できません");

        if (!this.isPathClear(piece, from, to))
            throw new IllegalArgumentException("移動経路に駒が存在します");

        if (this.hasPiece(to) && this.isOwner(targetPiece, player))
            throw new IllegalArgumentException(to.toString() + "に自分の駒が存在します");
    }

    private boolean hasPiece(Position position) {
        return board.hasPiece(position);
    }

    private boolean isOwner(Piece piece, Player player) {
        return piece.isOwner(player);
    }

    private boolean canMove(Piece piece, Position from, Position to) {
        return piece.canMove(from, to);
    }

    private boolean isPathClear(Piece piece, Position from, Position to) {
        return board.isPathClear(from, to);
    }
}
