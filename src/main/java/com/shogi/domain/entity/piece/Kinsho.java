package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public class Kinsho extends Piece implements PieceInterface {
    private static final String DISPLAY_NAME = "金";

    public Kinsho(Player player) {
        super(player);
    }

    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    public boolean canMoveTo(Position from, Position to) {
        int direction = (this.player == Player.SENTE) ? -1 : 1;
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();

        if (rowDiff == direction && Math.abs(colDiff) <= 1) return true;
        if (rowDiff == 0 && Math.abs(colDiff) == 1) return true;
        if (rowDiff == -direction && colDiff == 0) return true;
        return false;
    }
}
