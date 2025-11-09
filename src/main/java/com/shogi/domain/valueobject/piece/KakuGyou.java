
package com.shogi.domain.valueobject.piece;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.piece.promoted.Promotable;
import com.shogi.domain.valueobject.piece.promoted.RyuMa;

public class KakuGyou extends Piece implements Promotable {
    private static final String DISPLAY_NAME = "角";

    public KakuGyou(Player owner) {
        super(owner, DISPLAY_NAME);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        int yDiff = to.getY() - from.getY();
        int xDiff = to.getX() - from.getX();

        return Math.abs(yDiff) == Math.abs(xDiff) && yDiff != 0;
    }

    @Override
    public RyuMa promote() {
        return new RyuMa(this.owner);
    }
}
