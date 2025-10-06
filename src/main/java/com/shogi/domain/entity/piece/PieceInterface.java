package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;

public interface PieceInterface {
    String getDisplayName();
    boolean canMove(Position from, Position to);
}
