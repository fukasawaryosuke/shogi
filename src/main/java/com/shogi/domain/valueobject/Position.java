package com.shogi.domain.valueobject;

public class Position {
    private static final int MIN = 1;
    private static final int MAX = 9;

    private int row; // 行（1-9）
    private int col; // 列（1-9）

    public Position(int row, int col) {
        if (!this.isValid(row, col)) {
            throw new IllegalArgumentException("行と列は1から9の範囲で指定してください。");
        }
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    private boolean isValid(int row, int col) {
        return row >= this.MIN && row <= this.MAX && col >= this.MIN && col <= this.MAX;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position pos = (Position) o;
        return this.row == pos.row && this.col == pos.col;
    }

    @Override
    public int hashCode() {
        return 31 * row + col;
    }
}
