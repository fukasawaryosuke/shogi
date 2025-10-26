package com.shogi.domain.service;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Turn;
import com.shogi.domain.entity.Stand;
import com.shogi.domain.entity.piece.Piece;
import com.shogi.domain.factory.PieceFactory;

public class Capture {
  private final Stand stand;
  private final Turn turn;

  public Capture(Stand stand, Turn turn) {
    this.stand = stand;
    this.turn = turn;
  }

  public void capturePiece(Piece targetPiece) {
    Player currentPlayer = this.turn.getCurrentPlayer();

    if (!this.validateCapture(targetPiece, currentPlayer))
      return;

    Piece capturedPiece = PieceFactory.clonePiece(targetPiece, currentPlayer);
    stand.putPiece(currentPlayer, capturedPiece);
  }

  private boolean validateCapture(Piece targetPiece, Player currentPlayer) {
    if (targetPiece == null)
      return false;

    if (targetPiece.isOwner(currentPlayer))
      return false;

    return true;
  }
}
