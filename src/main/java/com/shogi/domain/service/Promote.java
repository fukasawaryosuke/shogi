package com.shogi.domain.service;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Turn;
import com.shogi.domain.entity.Board;
import com.shogi.domain.entity.piece.promoted.Promotable;
import com.shogi.domain.entity.piece.Piece;

public class Promote {
  private Board board;
  private Turn turn;

  public Promote(Board board, Turn turn) {
    this.board = board;
    this.turn = turn;
  }

  public Piece promotePiece(Promotable originPiece, Position to) {
    return originPiece.promote();
  }

  public boolean canChoosePromote(Position to) {
    Player currentPlayer = this.turn.getCurrentPlayer();
    return board.isEnemyZone(to, currentPlayer) && !board.isEnemyZoneOneRow(to, currentPlayer);
  }

  public boolean mustPromote(Position to) {
    Player currentPlayer = this.turn.getCurrentPlayer();
    return board.isEnemyZoneOneRow(to, currentPlayer);
  }
}
