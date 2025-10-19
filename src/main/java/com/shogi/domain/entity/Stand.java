package com.shogi.domain.entity;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.entity.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class Stand {
    private Map<Player, Map<Piece, Integer>> playerPieceMap;

    public Stand() {
        this.playerPieceMap = new HashMap<>();
        this.playerPieceMap.put(Player.SENTE, new HashMap<>());
        this.playerPieceMap.put(Player.GOTE, new HashMap<>());
    }

    // プレイヤーごとの持ち駒一覧を文字列で返す
    public String getCapturedPieces(Player player) {
        Map<Piece, Integer> pieceCounts = playerPieceMap.get(player);
        if (pieceCounts == null || pieceCounts.isEmpty()) {
            return "なし";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Piece, Integer> entry : pieceCounts.entrySet()) {
            String pieceName = entry.getKey().toString();
            int count = entry.getValue();
            sb.append(pieceName).append("*").append(count).append(" ");
        }
        return sb.toString().trim();
    }

    // プレイヤーの持ち駒に駒を追加
    public void addPiece(Player player, Piece piece) {
        Map<Piece, Integer> pieceCounts = playerPieceMap.computeIfAbsent(player, k -> new HashMap<>());
        pieceCounts.put(piece, pieceCounts.getOrDefault(piece, 0) + 1);
    }

    // プレイヤーの持ち駒から駒を減らす
    public void dropPiece(Player player, Piece piece) {
        Map<Piece, Integer> pieceCounts = playerPieceMap.get(player);
        if (pieceCounts == null) {
            return;
        }
        Integer count = pieceCounts.get(piece);
        if (count == null || count == 0) {
            return;
        }
        if (count == 1) {
            pieceCounts.remove(piece);
        } else {
            pieceCounts.put(piece, count - 1);
        }
    }

    public String toString(Player player) {
        return getCapturedPieces(player);
    }
}
