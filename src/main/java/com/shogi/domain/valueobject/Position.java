package com.shogi.domain.valueobject;

public class Position {
    private static final int MIN = 1;
    private static final int MAX = 9;

    private int col;
    private int row;

    public Position(int col, int row) {
        if (!this.isValid(col, row))
            throw new IllegalArgumentException("行と列は1から9の範囲で指定してください。");

        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return this.col;
    }

    public int getRow() {
        return this.row;
    }

    private boolean isValid(int row, int col) {
        return col >= MIN && col <= MAX && row >= MIN && row <= MAX;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Position))
            return false;

        Position position = (Position) obj;
        return this.col == position.col && this.row == position.row;
    }

    @Override
    public int hashCode() {
        return 31 * col + row;
    }

    @Override
    public String toString() {
        return col + ", " + row;
    }
}
