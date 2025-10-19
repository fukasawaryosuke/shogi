package com.shogi.domain.entity;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.entity.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class Stand {
    private Player owner;
    private Map<Piece, Integer> pieceMap;

    public Stand(Player player) {
        this.owner = player;
        this.pieceMap = new HashMap<>();
    }

    public String getCapturedPieces() {
        if (this.pieceMap.isEmpty()) {
            return "なし";
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Piece, Integer> entry : this.pieceMap.entrySet()) {
            String pieceName = entry.getKey().toString();
            int count = entry.getValue();
            sb.append(pieceName).append("*").append(count).append(" ");
        }
        return sb.toString().trim();
    }

    public void addPiece(Piece piece) {
        this.pieceMap.put(piece, this.pieceMap.getOrDefault(piece, 0) + 1);
    }

    public void dropPiece(Piece piece) {
        if (this.pieceMap == null) {
            // プレイヤーの持ち駒がない場合は何もしない
            return;
        }

        Integer count = this.pieceMap.get(piece);
        if (count == null || count == 0) {
            return;
        }

        if (count == 1) {
            // 駒の個数が1なら削除
            this.pieceMap.remove(piece);
        } else {
            // 個数が複数あれば1つ減らす
            this.pieceMap.put(piece, count - 1);
        }
    }

    @Override
    public String toString() {
        return getCapturedPieces();
    }
}
