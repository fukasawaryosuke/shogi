package com.shogi.domain.service;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Turn;
import com.shogi.domain.entity.Board;
import com.shogi.domain.entity.piece.KeiMa;
import com.shogi.domain.entity.piece.Piece;

public class Move {
    private final Board board;
    private final Turn turn;

    public Move(Board board, Turn turn) {
        this.board = board;
        this.turn = turn;
    }

    public void movePiece(Piece originPiece, Piece targetPiece, Position from, Position to) {
        this.validateMove(originPiece, targetPiece, from, to);

        this.board.removePiece(from);
        this.board.putPiece(to, originPiece);
    }

    private void validateMove(Piece originPiece, Piece targetPiece, Position from, Position to) {
        Player currentPlayer = this.turn.getCurrentPlayer();

        if (!board.hasPiece(from))
            throw new IllegalArgumentException(from.toString() + "に駒が存在しません");

        if (!originPiece.isOwner(currentPlayer))
            throw new IllegalArgumentException(currentPlayer + "の駒を選択してください");

        if (!originPiece.canMove(from, to))
            throw new IllegalArgumentException(originPiece.toString() + "は" + to.toString() + "に移動できません");

        if (!(originPiece instanceof KeiMa) && !board.isPathClear(from, to))
            throw new IllegalArgumentException("移動経路に駒が存在します");

        if (board.hasPiece(to) && targetPiece.isOwner(currentPlayer))
            throw new IllegalArgumentException(to.toString() + "に自分の駒が存在します");
    }
}
