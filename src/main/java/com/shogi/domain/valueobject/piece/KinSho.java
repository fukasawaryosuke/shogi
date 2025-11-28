package com.shogi.domain.valueobject.piece;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;

public class KinSho extends Piece {
    private static final String DISPLAY_NAME = "金";
    private static final PieceType PIECE_TYPE = PieceType.KI;

    public KinSho(Player owner) {
        super(owner, DISPLAY_NAME, PIECE_TYPE);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        int direction = (this.owner == Player.SENTE) ? -1 : 1;
        int yDiff = to.getY() - from.getY();
        int xDiff = to.getX() - from.getX();

        if (yDiff == direction && Math.abs(xDiff) <= 1)
            return true;
        if (yDiff == 0 && Math.abs(xDiff) == 1)
            return true;
        if (yDiff == -direction && xDiff == 0)
            return true;
        return false;
    }
}
