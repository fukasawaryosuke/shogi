
package com.shogi.domain.valueobject.piece;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.piece.promoted.Promotable;
import com.shogi.domain.valueobject.piece.promoted.RyuOu;

public class HiSha extends Piece implements Promotable {
    private static final String DISPLAY_NAME = "飛";
    private static final PieceType PIECE_TYPE = PieceType.HI;

    public HiSha(Player owner) {
        super(owner, DISPLAY_NAME, PIECE_TYPE);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        int yDiff = to.getY() - from.getY();
        int xDiff = to.getX() - from.getX();

        return (yDiff == 0 && xDiff != 0) || (yDiff != 0 && xDiff == 0);
    }

    @Override
    public RyuOu promote() {
        return new RyuOu(this.owner);
    }
}
