package com.shogi.domain.valueobject;

public class Position {
    private int row;    // 行（1-9）
    private int col;    // 列（1-9）

    // コンストラクタ
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // ゲッター（値を取得）
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    // セッター（値を設定）
    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    // 位置が有効かチェック
    public boolean isValid() {
        return row >= 1 && row <= 9 && col >= 1 && col <= 9;
    }

    // 文字列表現
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}
