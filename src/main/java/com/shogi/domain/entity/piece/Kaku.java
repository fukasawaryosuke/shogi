
package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public class Kaku extends Piece implements Promotable {
    private static final String DISPLAY_NAME = "角";

    public Kaku(Player player) {
        super(player, DISPLAY_NAME);
    }

    public boolean canMove(Position from, Position to) {
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();
        return Math.abs(rowDiff) == Math.abs(colDiff) && rowDiff != 0;
    }

    public void promote() {
        // return new Ryuuma(this.owner);
    }
}
