package com.shogi.domain.valueobject.piece;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;

public class OuSho extends Piece {
    private static final String DISPLAY_NAME_SENTE = "王";
    private static final String DISPLAY_NAME_GOTE = "玉";
    private static final PieceType PIECE_TYPE = PieceType.OU;

    public OuSho(Player owner) {
        super(owner, owner == Player.SENTE ? DISPLAY_NAME_SENTE : DISPLAY_NAME_GOTE, PIECE_TYPE);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        int yDiff = Math.abs(to.getY() - from.getY());
        int xDiff = Math.abs(to.getX() - from.getX());

        return (yDiff <= 1 && xDiff <= 1) && !(yDiff == 0 && xDiff == 0);
    }
}
