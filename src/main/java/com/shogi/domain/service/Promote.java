package com.shogi.domain.service;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Turn;
import com.shogi.domain.entity.Board;
import com.shogi.domain.entity.piece.promoted.Promotable;
import com.shogi.domain.entity.piece.Piece;

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

    if (!board.isEnemyZoneOneRow(to, currentPlayer))
      return false;

    return true;
  }

  public boolean mustPromote(Position to) {
    Piece piece = this.board.getPiece(to);
    Player currentPlayer = this.turn.getCurrentPlayer();

    if (!isPromotable(piece))
      return false;

    if (!board.isEnemyZoneOneRow(to, currentPlayer))
      return false;

    return true;
  }

  private boolean isPromotable(Piece piece) {
    return piece instanceof Promotable;
  }
}
