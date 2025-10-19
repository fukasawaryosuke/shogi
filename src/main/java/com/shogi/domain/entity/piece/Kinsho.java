package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public class Kinsho extends Piece {
    private static final String DISPLAY_NAME = "金";

    public Kinsho(Player player) {
        super(player, DISPLAY_NAME);
    }

    public boolean canMove(Position from, Position to) {
        int direction = (this.owner == Player.SENTE) ? -1 : 1;
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();

        if (rowDiff == direction && Math.abs(colDiff) <= 1)
            return true;
        if (rowDiff == 0 && Math.abs(colDiff) == 1)
            return true;
        if (rowDiff == -direction && colDiff == 0)
            return true;
        return false;
    }
}
