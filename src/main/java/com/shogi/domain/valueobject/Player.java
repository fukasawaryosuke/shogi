package com.shogi.domain.valueobject;

/**
 * 将棋の先手・後手を表す列挙型
 */
public enum Player {
    SENTE,
    GOTE;

    @Override
    public String toString() {
        switch (this) {
            case SENTE:
                return "先手";
            case GOTE:
                return "後手";
            default:
                return super.toString();
        }
    }
}
