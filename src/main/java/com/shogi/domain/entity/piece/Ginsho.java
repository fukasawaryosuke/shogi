package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.entity.piece.promoted.Promotable;
import com.shogi.domain.entity.piece.promoted.NariGin;

public class GinSho extends Piece implements Promotable {
    private static final String DISPLAY_NAME = "銀";

    public GinSho(Player owner) {
        super(owner, DISPLAY_NAME);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        int direction = (this.owner == Player.SENTE) ? -1 : 1;
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();

        if (colDiff == 0 && rowDiff == direction)
            return true;
        if (Math.abs(colDiff) == 1) {
            if (rowDiff == direction || rowDiff == -direction)
                return true;
        }
        return false;
    }

    @Override
    public NariGin promote() {
        return new NariGin(this.owner);
    }
}
