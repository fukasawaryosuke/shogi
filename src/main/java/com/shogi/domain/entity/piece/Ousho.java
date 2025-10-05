package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public class Ousho extends Piece implements PieceInterface {
    private static final String DISPLAY_NAME_SENTE = "王";
    private static final String DISPLAY_NAME_GOTE = "玉";

    public Ousho(Position position, Player player) {
        super(position, player);
    }

    public String getDisplayName() {
        return player == Player.SENTE ? DISPLAY_NAME_SENTE : DISPLAY_NAME_GOTE;
    }

    public boolean canMoveTo(Position newPosition) {
        int rowDiff = Math.abs(newPosition.getRow() - this.position.getRow());
        int colDiff = Math.abs(newPosition.getCol() - this.position.getCol());

        return (rowDiff <= 1 && colDiff <= 1) && !(rowDiff == 0 && colDiff == 0);
    }
}
