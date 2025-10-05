
package com.shogi.domain.entity.piece;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

public class Kaku extends PromotablePiece implements PieceInterface {
    private static final String DISPLAY_NAME = "角";

    public Kaku(Position position, Player player) {
        super(position, player);
    }

    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    public boolean canMoveTo(Position newPosition) {
        // 現在位置から移動先の縦方向の差
        int rowDiff = newPosition.getRow() - this.position.getRow();
        // 現在位置から移動先の横方向の差
        int colDiff = newPosition.getCol() - this.position.getCol();
        // 縦方向の差の絶対値と横方向の差の絶対値が等しい
        return Math.abs(rowDiff) == Math.abs(colDiff) && rowDiff != 0;
    }
}
