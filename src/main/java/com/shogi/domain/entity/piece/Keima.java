
package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public class Keima extends PromotablePiece implements PieceInterface {
    private static final String DISPLAY_NAME = "桂";

    public Keima(Player player) {
        super(player);
    }

    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    public boolean canMove(Position from, Position to) {
        int direction = (this.player == Player.SENTE) ? -1 : 1;
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = Math.abs(to.getCol() - from.getCol());
        return rowDiff == 2 * direction && colDiff == 1;
    }
}
