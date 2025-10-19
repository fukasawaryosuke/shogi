package com.shogi.domain.valueobject;

public enum Player {
    SENTE, GOTE;

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
