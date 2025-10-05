package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public class Kyosha extends PromotablePiece implements PieceInterface {
    private static final String DISPLAY_NAME = "香";

    public Kyosha(Position position, Player player) {
        super(position, player);
    }

    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    public boolean canMoveTo(Position newPosition) {
        if (newPosition.getCol() != this.position.getCol()) return false;
        int direction = (this.player == Player.SENTE) ? -1 : 1;
        int rowDiff = newPosition.getRow() - this.position.getRow();
        return direction * rowDiff > 0;
    }
}
