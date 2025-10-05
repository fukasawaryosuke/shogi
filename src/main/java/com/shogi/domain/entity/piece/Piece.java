package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public class Piece {
    protected Position position;
    protected Player player;

    public Piece(Position position, Player player) {
        this.position = position;
        this.player = player;
    }
}
