package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public class Fu extends PromotablePiece implements PieceInterface {
    private static final String DISPLAY_NAME = "歩";

    public Fu(Player player) {
        super(player);
    }

    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    public boolean canMove(Position from, Position to) {
        int direction = (this.player == Player.SENTE) ? -1 : 1;
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();
        return rowDiff == direction && colDiff == 0;
    }
}
