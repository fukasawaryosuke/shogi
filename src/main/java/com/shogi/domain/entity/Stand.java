package com.shogi.domain.entity;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.entity.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class Stand {
    private Map<Player, Map<Piece, Integer>> standMap;

    public Stand() {
        this.standMap = new HashMap<>();
        this.standMap.put(Player.SENTE, new HashMap<>());
        this.standMap.put(Player.GOTE, new HashMap<>());
    }

    public boolean hasPiece(Player player, Piece piece) {
        Map<Piece, Integer> pieceCounts = standMap.get(player);
        if (pieceCounts == null || pieceCounts.isEmpty())
            return false;

        Integer count = pieceCounts.get(piece);
        return count != null && count > 0;
    }

    public String getPieces(Player player) {
        Map<Piece, Integer> pieceCounts = standMap.get(player);
        if (pieceCounts == null || pieceCounts.isEmpty())
            return "なし";
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Piece, Integer> entry : pieceCounts.entrySet()) {
            String pieceName = entry.getKey().toString();
            int count = entry.getValue();
            sb.append(pieceName).append("*").append(count).append(" ");
        }
        return sb.toString().trim();
    }

    public void putPiece(Player player, Piece piece) {
        Map<Piece, Integer> pieceCounts = standMap.computeIfAbsent(player, k -> new HashMap<>());
        pieceCounts.merge(piece, 1, Integer::sum);
    }

    public void removePiece(Player player, Piece piece) {
        Map<Piece, Integer> pieceCounts = standMap.get(player);
        if (pieceCounts == null || pieceCounts.isEmpty())
            return;
        pieceCounts.computeIfPresent(piece, (p, cnt) -> cnt <= 1 ? null : cnt - 1);
    }

    public String toString(Player player) {
        return getPieces(player);
    }
}
