package com.shogi.domain.valueobject.piece.promoted;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.piece.Piece;

public class RyuOu extends Piece {
  private static final String DISPLAY_NAME = "龍";

  public RyuOu(Player owner) {
    super(owner, DISPLAY_NAME);
  }

  @Override
  public boolean canMove(Position from, Position to) {
    int rowDiff = Math.abs(to.getRow() - from.getRow());
    int colDiff = Math.abs(to.getCol() - from.getCol());

    if ((rowDiff == 0 && colDiff != 0) || (colDiff == 0 && rowDiff != 0))
      return true;
    if ((rowDiff <= 1 && colDiff <= 1) && (rowDiff + colDiff != 0))
      return true;
    return false;
  }
}
