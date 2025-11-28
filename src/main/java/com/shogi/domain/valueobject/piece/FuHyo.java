package com.shogi.domain.valueobject.piece;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.piece.promoted.Promotable;
import com.shogi.domain.valueobject.piece.promoted.ToKin;

public class FuHyo extends Piece implements Promotable {
    private static final String DISPLAY_NAME = "歩";
    private static final PieceType PIECE_TYPE = PieceType.FU;

    public FuHyo(Player owner) {
        super(owner, DISPLAY_NAME, PIECE_TYPE);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        int direction = (this.owner == Player.SENTE) ? -1 : 1;
        int yDiff = to.getY() - from.getY();
        int xDiff = to.getX() - from.getX();

        return yDiff == direction && xDiff == 0;
    }

    @Override
    public ToKin promote() {
        return new ToKin(this.owner);
    }
}
