package com.shogi.domain.entity.piece;

public interface Promotable{
    default boolean canPromote(){
        return true;
    }
    void promote();
}
