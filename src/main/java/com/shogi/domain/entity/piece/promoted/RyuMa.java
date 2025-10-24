package com.shogi.domain.entity.piece.promoted;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.entity.piece.Piece;

public class RyuMa extends Piece {
  private static final String DISPLAY_NAME = "馬";

  public RyuMa(Player owner) {
    super(owner, DISPLAY_NAME);
  }

  @Override
  public boolean canMove(Position from, Position to) {
    int rowDiff = Math.abs(to.getRow() - from.getRow());
    int colDiff = Math.abs(to.getCol() - from.getCol());

    if (rowDiff == colDiff && rowDiff != 0)
      return true;
    if ((rowDiff <= 1 && colDiff <= 1) && (rowDiff + colDiff != 0))
      return true;
    return false;
  }
}
