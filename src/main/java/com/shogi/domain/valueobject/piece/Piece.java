package com.shogi.domain.valueobject.piece;

import java.util.Objects;
import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;

public abstract class Piece {
    protected Player owner;
    protected String displayName;
    protected PieceType type;

    public Piece(Player owner, String displayName, PieceType type) {
        this.owner = owner;
        this.displayName = displayName;
        this.type = type;
    }

    public boolean isOwner(Player player) {
        return this.owner.equals(player);
    }

    public String getOwner() {
        return this.owner.toString();
    }

    public PieceType getType() {
        return this.type;
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
