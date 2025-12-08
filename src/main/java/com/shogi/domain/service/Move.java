package com.shogi.domain.service;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Turn;
import com.shogi.domain.valueobject.piece.KeiMa;
import com.shogi.domain.valueobject.piece.Piece;
import com.shogi.domain.entity.Board;

public class Move {
    private final Board board;
    private final Turn turn;
    private final CheckMate checkMateService;

    public Move(Board board, Turn turn) {
        this.board = board;
        this.turn = turn;
        this.checkMateService = new CheckMate(board);
    }

    public void movePiece(Piece originPiece, Piece targetPiece, Position from, Position to) {
        Player currentPlayer = this.turn.getCurrentPlayer();
        this.validateMove(originPiece, targetPiece, from, to);

        // 一時的に駒を動かす
        this.board.removePiece(from);
        Piece captured = this.board.getPiece(to);
        this.board.putPiece(to, originPiece);

        // この手を指した後も王手されているか、または王手されたままかチェック
        if (checkMateService.isInCheck(currentPlayer)) {
            // 元に戻す
            this.board.putPiece(from, originPiece);
            if (captured != null) {
                this.board.putPiece(to, captured);
            } else {
                this.board.removePiece(to);
            }
            throw new IllegalArgumentException("王手を回避してください");
        }
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
