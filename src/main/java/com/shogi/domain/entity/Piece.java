package com.shogi.domain.entity;

import com.shogi.domain.valueobject.Position;

public class Piece {
    private String name;        // 駒の名前
    private String color;       // 先手/後手
    private Position position;  // 現在の位置
    private boolean isPromoted; // 成っているか

    // コンストラクタ
    public Piece(String name, String color, Position position) {
        this.name = name;
        this.color = color;
        this.position = position;
        this.isPromoted = false;
    }

    // ゲッター
    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isPromoted() {
        return isPromoted;
    }

    // 駒を動かす
    public void moveTo(Position newPosition) {
        this.position = newPosition;
    }

    // 成る
    public void promote() {
        this.isPromoted = true;
    }

    // 駒の表示名
    public String getDisplayName() {
        if (isPromoted) {
            return "成" + name;
        }
        return name;
    }
}
