
package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.entity.piece.promoted.Promotable;
import com.shogi.domain.entity.piece.promoted.NariKei;

public class KeiMa extends Piece implements Promotable {
    private static final String DISPLAY_NAME = "桂";

    public KeiMa(Player owner) {
        super(owner, DISPLAY_NAME);
    }

    public boolean canMove(Position from, Position to) {
        int direction = (this.owner == Player.SENTE) ? -1 : 1;
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = Math.abs(to.getCol() - from.getCol());
        return rowDiff == 2 * direction && colDiff == 1;
    }

    public NariKei promote() {
        return new NariKei(this.owner);
    }
}
