package com.shogi.domain.valueobject;

public enum Player {
    SENTE, GOTE;

    /**
     * 相手のプレイヤーを取得する
     * 
     * @return 先手なら後手、後手なら先手
     */
    public Player reverse() {
        return this == SENTE ? GOTE : SENTE;
    }

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
