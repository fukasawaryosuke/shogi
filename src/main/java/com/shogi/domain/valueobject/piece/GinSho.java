package com.shogi.domain.valueobject.piece;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.piece.promoted.Promotable;
import com.shogi.domain.valueobject.piece.promoted.NariGin;

public class GinSho extends Piece implements Promotable {
    private static final String DISPLAY_NAME = "銀";
    private static final PieceType PIECE_TYPE = PieceType.GI;

    public GinSho(Player owner) {
        super(owner, DISPLAY_NAME, PIECE_TYPE);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        int direction = (this.owner == Player.SENTE) ? -1 : 1;
        int yDiff = to.getY() - from.getY();
        int xDiff = to.getX() - from.getX();

        if (xDiff == 0 && yDiff == direction)
            return true;
        if (Math.abs(xDiff) == 1) {
            if (yDiff == direction || yDiff == -direction)
                return true;
        }
        return false;
    }

    @Override
    public NariGin promote() {
        return new NariGin(this.owner);
    }
}
