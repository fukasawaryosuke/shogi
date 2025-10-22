package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.entity.piece.promoted.Promotable;
import com.shogi.domain.entity.piece.promoted.NariKyo;

public class KyoSha extends Piece implements Promotable {
    private static final String DISPLAY_NAME = "香";

    public KyoSha(Player owner) {
        super(owner, DISPLAY_NAME);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        if (to.getCol() != from.getCol())
            return false;
        int direction = (this.owner == Player.SENTE) ? -1 : 1;
        int rowDiff = to.getRow() - from.getRow();
        return direction * rowDiff > 0;
    }

    @Override
    public NariKyo promote() {
        return new NariKyo(this.owner);
    }
}
