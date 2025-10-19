package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public class Kyosha extends Piece implements Promotable {
    private static final String DISPLAY_NAME = "香";

    public Kyosha(Player player) {
        super(player, DISPLAY_NAME);
    }

    public boolean canMove(Position from, Position to) {
        if (to.getCol() != from.getCol())
            return false;
        int direction = (this.owner == Player.SENTE) ? -1 : 1;
        int rowDiff = to.getRow() - from.getRow();
        return direction * rowDiff > 0;
    }

    public void promote() {
        // return new Narikyo(this.owner);
    }
}
