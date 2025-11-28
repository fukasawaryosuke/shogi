package com.shogi.domain.valueobject.piece.promoted;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.piece.Piece;
import com.shogi.domain.valueobject.piece.PieceType;

public class RyuMa extends Piece {
  private static final String DISPLAY_NAME = "馬";
  private static final PieceType PIECE_TYPE = PieceType.KA;

  public RyuMa(Player owner) {
    super(owner, DISPLAY_NAME, PIECE_TYPE);
  }

  @Override
  public boolean canMove(Position from, Position to) {
    int yDiff = Math.abs(to.getY() - from.getY());
    int xDiff = Math.abs(to.getX() - from.getX());

    if (yDiff == xDiff && yDiff != 0)
      return true;
    if ((yDiff <= 1 && xDiff <= 1) && (yDiff + xDiff != 0))
      return true;
    return false;
  }
}
