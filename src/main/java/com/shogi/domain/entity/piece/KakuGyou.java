
package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.entity.piece.promoted.Promotable;
import com.shogi.domain.entity.piece.promoted.RyuMa;

public class KakuGyou extends Piece implements Promotable {
    private static final String DISPLAY_NAME = "角";

    public KakuGyou(Player owner) {
        super(owner, DISPLAY_NAME);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();
        return Math.abs(rowDiff) == Math.abs(colDiff) && rowDiff != 0;
    }

    @Override
    public RyuMa promote() {
        return new RyuMa(this.owner);
    }
}
