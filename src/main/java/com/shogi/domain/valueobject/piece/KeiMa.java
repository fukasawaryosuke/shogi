
package com.shogi.domain.valueobject.piece;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.piece.promoted.Promotable;
import com.shogi.domain.valueobject.piece.promoted.NariKei;

public class KeiMa extends Piece implements Promotable {
    private static final String DISPLAY_NAME = "桂";
    private static final PieceType PIECE_TYPE = PieceType.KE;

    public KeiMa(Player owner) {
        super(owner, DISPLAY_NAME, PIECE_TYPE);
    }

    @Override
    public boolean canMove(Position from, Position to) {
        int direction = (this.owner == Player.SENTE) ? -1 : 1;
        int yDiff = to.getY() - from.getY();
        int xDiff = Math.abs(to.getX() - from.getX());

        return yDiff == 2 * direction && xDiff == 1;
    }

    public NariKei promote() {
        return new NariKei(this.owner);
    }
}
