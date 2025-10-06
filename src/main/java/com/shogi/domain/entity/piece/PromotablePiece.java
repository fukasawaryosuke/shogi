package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public class PromotablePiece extends Piece {
    private boolean isPromoted;

    public PromotablePiece(Player player) {
        super(player);
        this.isPromoted = false;
    }

    public boolean isPromoted() {
        return isPromoted;
    }

    public void promote() {
        isPromoted = true;
    }

    public void demote() {
        isPromoted = false;
    }
}
