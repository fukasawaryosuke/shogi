package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public class Kyosha extends PromotablePiece implements PieceInterface {
    private static final String DISPLAY_NAME = "香";

    public Kyosha(Player player) {
        super(player);
    }

    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    public boolean canMove(Position from, Position to) {
        if (to.getCol() != from.getCol()) return false;
        int direction = (this.player == Player.SENTE) ? -1 : 1;
        int rowDiff = to.getRow() - from.getRow();
        return direction * rowDiff > 0;
    }
}
