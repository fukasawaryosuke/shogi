package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.entity.piece.promoted.Promotable;
import com.shogi.domain.entity.piece.promoted.ToKin;

public class FuHyo extends Piece implements Promotable {
    private static final String DISPLAY_NAME = "歩";

    public FuHyo(Player owner) {
        super(owner, DISPLAY_NAME);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        int direction = (this.owner == Player.SENTE) ? -1 : 1;
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();
        return rowDiff == direction && colDiff == 0;
    }

    @Override
    public ToKin promote() {
        return new ToKin(this.owner);
    }
}
