package com.shogi.domain.entity;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Turn;

public class Piece {
    private String name;        // 駒の名前
    private Turn turn;        // 先手/後手
    private Position position;  // 現在の位置
    private boolean isPromoted; // 成っているか

    // コンストラクタ
    public Piece(String name, Turn turn, Position position) {
        this.name = name;
        this.turn = turn;
        this.position = position;
        this.isPromoted = false;
    }

    // 駒の表示名
    public String getDisplayName() {
        if (isPromoted) {
            return "成" + name;
        }
        return name;
    }
}
