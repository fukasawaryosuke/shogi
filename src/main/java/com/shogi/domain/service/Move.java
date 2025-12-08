package com.shogi.domain.service;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Turn;
import com.shogi.domain.valueobject.piece.KeiMa;
import com.shogi.domain.valueobject.piece.KyoSha;
import com.shogi.domain.valueobject.piece.FuHyo;
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

        // 行き所のない駒の禁止
        if (isDeadPosition(originPiece, to, currentPlayer)) {
            throw new IllegalArgumentException("その位置に駒を動かすと動けなくなります");
        }
    }

    /**
     * 駒が動けなくなる位置かどうかをチェック
     */
    private boolean isDeadPosition(Piece piece, Position to, Player player) {
        int y = to.getY();

        // 先手の場合
        if (player == Player.SENTE) {
            // 歩: 1段目に置けない
            if (piece instanceof FuHyo && y == 1) {
                return true;
            }
            // 香車: 1段目に置けない
            if (piece instanceof KyoSha && y == 1) {
                return true;
            }
            // 桂馬: 1-2段目に置けない
            if (piece instanceof KeiMa && (y == 1 || y == 2)) {
                return true;
            }
        }
        // 後手の場合
        else if (player == Player.GOTE) {
            // 歩: 9段目に置けない
            if (piece instanceof FuHyo && y == 9) {
                return true;
            }
            // 香車: 9段目に置けない
            if (piece instanceof KyoSha && y == 9) {
                return true;
            }
            // 桂馬: 8-9段目に置けない
            if (piece instanceof KeiMa && (y == 8 || y == 9)) {
                return true;
            }
        }

        return false;
    }
}
