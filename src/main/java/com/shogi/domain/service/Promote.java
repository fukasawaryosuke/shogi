package com.shogi.domain.service;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Turn;
import com.shogi.domain.entity.Board;
import com.shogi.domain.valueobject.piece.promoted.Promotable;
import com.shogi.domain.valueobject.piece.Piece;
import com.shogi.domain.valueobject.piece.KeiMa;
import com.shogi.domain.valueobject.piece.KyoSha;
import com.shogi.domain.valueobject.piece.FuHyo;

public class Promote {
  private final Board board;
  private final Turn turn;

  public Promote(Board board, Turn turn) {
    this.board = board;
    this.turn = turn;
  }

  public void promotePiece(Position to) {
    Piece originPiece = this.board.getPiece(to);

    if (!isPromotable(originPiece))
      return;

    Piece promotedPiece = ((Promotable) originPiece).promote();
    this.board.putPiece(to, promotedPiece);
  }

  public boolean canChoosePromote(Position to) {
    Piece piece = this.board.getPiece(to);
    Player currentPlayer = this.turn.getCurrentPlayer();

    if (!isPromotable(piece))
      return false;

    if (board.isEnemyZone(to, currentPlayer))
      return true;

    if (!board.isEnemyZoneEdge(to, currentPlayer))
      return false;

    return true;
  }

  public boolean mustPromote(Position to) {
    Piece piece = this.board.getPiece(to);
    Player currentPlayer = this.turn.getCurrentPlayer();

    if (!isPromotable(piece))
      return false;

    int y = to.getY();

    // 先手の場合
    if (currentPlayer == Player.SENTE) {
      // 桂馬: 1段目は必ず成る
      if (piece instanceof KeiMa && y == 1) {
        return true;
      }
      // 香車: 1段目は必ず成る
      if (piece instanceof KyoSha && y == 1) {
        return true;
      }
      // 歩: 1段目は必ず成る
      if (piece instanceof FuHyo && y == 1) {
        return true;
      }
      // 桂馬: 2段目は必ず成る
      if (piece instanceof KeiMa && y == 2) {
        return true;
      }
    }
    // 後手の場合
    else if (currentPlayer == Player.GOTE) {
      // 桂馬: 9段目は必ず成る
      if (piece instanceof KeiMa && y == 9) {
        return true;
      }
      // 香車: 9段目は必ず成る
      if (piece instanceof KyoSha && y == 9) {
        return true;
      }
      // 歩: 9段目は必ず成る
      if (piece instanceof FuHyo && y == 9) {
        return true;
      }
      // 桂馬: 8段目は必ず成る
      if (piece instanceof KeiMa && y == 8) {
        return true;
      }
    }

    return false;
  }

  private boolean isPromotable(Piece piece) {
    return piece instanceof Promotable;
  }
}
