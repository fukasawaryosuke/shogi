
package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public class Keima extends PromotablePiece implements PieceInterface {
    private static final String DISPLAY_NAME = "桂";

    public Keima(Position position, Player player) {
        super(position, player);
    }

    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    public boolean canMoveTo(Position newPosition) {
        int direction = (this.player == Player.SENTE) ? -1 : 1;
        int rowDiff = newPosition.getRow() - this.position.getRow();
        int colDiff = Math.abs(newPosition.getCol() - this.position.getCol());
        return rowDiff == 2 * direction && colDiff == 1;
    }
}
