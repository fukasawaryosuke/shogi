package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public class Piece {
    protected Player owner;

    public Piece(Player player) {
        this.owner = player;
    }

    public boolean isOwner(Player player) {
        return this.owner.equals(player);
    }
}
