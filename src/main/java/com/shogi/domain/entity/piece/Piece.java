package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public class Piece {
    protected Player player;

    public Piece(Player player) {
        this.player = player;
    }
}
