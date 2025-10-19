package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public class Fu extends Piece implements Promotable {
    private static final String DISPLAY_NAME = "歩";

    public Fu(Player player) {
        super(player, DISPLAY_NAME);
    }

    public boolean canMove(Position from, Position to) {
        int direction = (this.owner == Player.SENTE) ? -1 : 1;
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();
        return rowDiff == direction && colDiff == 0;
    }

    public void promote() {
        // return new Tokin(this.owner);
    }
}
