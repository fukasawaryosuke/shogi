package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public class Ousho extends Piece {
    private static final String DISPLAY_NAME_SENTE = "王";
    private static final String DISPLAY_NAME_GOTE = "玉";

    public Ousho(Player player) {
        super(player, player == Player.SENTE ? DISPLAY_NAME_SENTE : DISPLAY_NAME_GOTE);
    }

    public boolean canMove(Position from, Position to) {
        int rowDiff = Math.abs(to.getRow() - from.getRow());
        int colDiff = Math.abs(to.getCol() - from.getCol());

        return (rowDiff <= 1 && colDiff <= 1) && !(rowDiff == 0 && colDiff == 0);
    }
}
