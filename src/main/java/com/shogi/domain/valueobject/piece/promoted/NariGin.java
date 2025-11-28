package com.shogi.domain.valueobject.piece.promoted;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.piece.Piece;
import com.shogi.domain.valueobject.piece.PieceType;

public class NariGin extends Piece {
    private static final String DISPLAY_NAME = "成銀";
    private static final PieceType PIECE_TYPE = PieceType.GI;

    public NariGin(Player owner) {
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
