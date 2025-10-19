
package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public class Hisha extends Piece implements Promotable {
    private static final String DISPLAY_NAME = "飛";

    public Hisha(Player player) {
        super(player, DISPLAY_NAME);
    }

    public boolean canMove(Position from, Position to) {
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();
        return (rowDiff == 0 && colDiff != 0) || (rowDiff != 0 && colDiff == 0);
    }

    public void promote() {
        // return new Ryuou(this.owner);
    }
}
