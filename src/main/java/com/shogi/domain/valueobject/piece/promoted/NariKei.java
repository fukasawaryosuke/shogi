package com.shogi.domain.valueobject.piece.promoted;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.piece.Piece;

public class NariKei extends Piece {
    private static final String DISPLAY_NAME = "成桂";

    public NariKei(Player owner) {
        super(owner, DISPLAY_NAME);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        int direction = (this.owner == Player.SENTE) ? -1 : 1;
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();

        if (rowDiff == direction && Math.abs(colDiff) <= 1)
            return true;
        if (rowDiff == 0 && Math.abs(colDiff) == 1)
            return true;
        if (rowDiff == -direction && colDiff == 0)
            return true;
        return false;
    }
}
