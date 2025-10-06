
package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public class Hisha extends PromotablePiece implements PieceInterface {
    private static final String DISPLAY_NAME = "飛";

    public Hisha(Player player) {
        super(player);
    }

    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    public boolean canMove(Position from, Position to) {
        // 現在位置から移動先の縦方向の差
        int rowDiff = to.getRow() - from.getRow();
        // 現在位置から移動先の横方向の差
        int colDiff = to.getCol() - from.getCol();
        // 縦方向の差が0で横方向の差が0でない、またはその逆
        return (rowDiff == 0 && colDiff != 0) || (rowDiff != 0 && colDiff == 0);
    }
}
