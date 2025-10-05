package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public class Fu extends PromotablePiece implements PieceInterface {
    private static final String DISPLAY_NAME = "歩";

    public Fu(Position position, Player player) {
        super(position, player);
    }

    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    public boolean canMoveTo(Position newPosition) {
        int direction = (this.player == Player.SENTE) ? -1 : 1;
        int rowDiff = newPosition.getRow() - this.position.getRow();
        int colDiff = newPosition.getCol() - this.position.getCol();
        return rowDiff == direction && colDiff == 0;
    }
}
