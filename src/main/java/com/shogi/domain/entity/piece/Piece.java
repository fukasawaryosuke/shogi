package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;

public abstract class Piece {
    protected Player owner;
    protected String displayName;

    public Piece(Player owner, String displayName) {
        this.owner = owner;
        this.displayName = displayName;
    }

    public boolean isOwner(Player player) {
        return this.owner.equals(player);
    }

    public abstract boolean canMove(Position from, Position to);

    @Override
    public String toString() {
        return this.displayName;
    }
}
