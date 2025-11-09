package com.shogi.domain.valueobject;

import java.util.Objects;

public class Position {
    private static final int MIN = 1;
    private static final int MAX = 9;

    private int x; // 列
    private int y; // 行

    public Position(int x, int y) {
        if (!this.isValid(x, y))
            throw new IllegalArgumentException("行と列は1から9の範囲で指定してください。");

        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    private boolean isValid(int x, int y) {
        return x >= MIN && x <= MAX && y >= MIN && y <= MAX;
    }

    @Override
    public String toString() {
        return String.format("%d, %d", x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Position))
            return false;
        Position other = (Position) obj;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
