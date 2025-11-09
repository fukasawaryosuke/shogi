package com.shogi.domain.valueobject.piece;

import java.util.Objects;
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

    public String getOwner() {
        return this.owner.toString();
    }

    public abstract boolean canMove(Position from, Position to);

    @Override
    public String toString() {
        return this.displayName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Piece other = (Piece) obj;
        return Objects.equals(this.owner, other.owner) &&
                Objects.equals(this.displayName, other.displayName);
    }

    @Override
    public int hashCode() {
        int hash = Objects.hash(owner, displayName);
        return hash;
    }
}
