
package com.shogi.domain.valueobject.piece;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.piece.promoted.Promotable;
import com.shogi.domain.valueobject.piece.promoted.RyuOu;

public class HiSha extends Piece implements Promotable {
    private static final String DISPLAY_NAME = "飛";

    public HiSha(Player owner) {
        super(owner, DISPLAY_NAME);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();

        return (rowDiff == 0 && colDiff != 0) || (rowDiff != 0 && colDiff == 0);
    }

    @Override
    public RyuOu promote() {
        return new RyuOu(this.owner);
    }
}
