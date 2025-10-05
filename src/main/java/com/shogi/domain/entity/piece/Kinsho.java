package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public class Kinsho extends Piece implements PieceInterface {
    private static final String DISPLAY_NAME = "金";

    public Kinsho(Position position, Player player) {
        super(position, player);
    }

    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    public boolean canMoveTo(Position newPosition) {
        int direction = (this.player == Player.SENTE) ? -1 : 1;
        int rowDiff = newPosition.getRow() - this.position.getRow();
        int colDiff = newPosition.getCol() - this.position.getCol();

        if (rowDiff == direction && Math.abs(colDiff) <= 1) return true;
        if (rowDiff == 0 && Math.abs(colDiff) == 1) return true;
        if (rowDiff == -direction && colDiff == 0) return true;
        return false;
    }
}
