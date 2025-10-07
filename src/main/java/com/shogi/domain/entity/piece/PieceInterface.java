package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public interface PieceInterface {
    String getDisplayName();
    boolean canMove(Position from, Position to);
    boolean isOwner(Player player);
}
