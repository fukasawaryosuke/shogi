package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public class Ginsho extends Piece implements Promotable {
    private static final String DISPLAY_NAME = "銀";

    public Ginsho(Player player) {
        super(player, DISPLAY_NAME);
    }

    public boolean canMove(Position from, Position to) {
        int direction = (this.owner == Player.SENTE) ? -1 : 1;
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();

        if (colDiff == 0 && rowDiff == direction)
            return true;
        if (Math.abs(colDiff) == 1) {
            if (rowDiff == direction || rowDiff == -direction)
                return true;
        }
        return false;
    }

    public void promote() {
        // return new Narigin(this.owner);
    }
}
