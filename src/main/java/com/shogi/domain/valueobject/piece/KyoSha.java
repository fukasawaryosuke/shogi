package com.shogi.domain.valueobject.piece;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.piece.promoted.Promotable;
import com.shogi.domain.valueobject.piece.promoted.NariKyo;

public class KyoSha extends Piece implements Promotable {
    private static final String DISPLAY_NAME = "香";

    public KyoSha(Player owner) {
        super(owner, DISPLAY_NAME);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        if (to.getX() != from.getX())
            return false;

        int direction = (this.owner == Player.SENTE) ? -1 : 1;
        int yDiff = to.getY() - from.getY();

        return direction * yDiff > 0;
    }

    @Override
    public NariKyo promote() {
        return new NariKyo(this.owner);
    }
}
