
package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public class Keima extends Piece implements Promotable {
    private static final String DISPLAY_NAME = "桂";

    public Keima(Player player) {
        super(player, DISPLAY_NAME);
    }

    public boolean canMove(Position from, Position to) {
        int direction = (this.owner == Player.SENTE) ? -1 : 1;
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = Math.abs(to.getCol() - from.getCol());
        return rowDiff == 2 * direction && colDiff == 1;
    }

    public void promote() {
        // return new Narikei(this.owner);
    }
}
