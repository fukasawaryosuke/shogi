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

    public boolean hasPiece(Player player, Piece piece) {
        Map<Piece, Integer> pieceCounts = playerPieceMap.get(player);
        if (pieceCounts == null || pieceCounts.isEmpty())
            return false;

        Integer count = pieceCounts.get(piece);
        return count != null && count > 0;
    }

    public String getPieces(Player player) {
        Map<Piece, Integer> pieceCounts = playerPieceMap.get(player);
        if (pieceCounts.isEmpty())
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
        Map<Piece, Integer> pieceCounts = playerPieceMap.computeIfAbsent(player, k -> new HashMap<>());
        pieceCounts.put(piece, pieceCounts.getOrDefault(piece, 0) + 1);
    }

    public void removePiece(Player player, Piece piece) {
        Map<Piece, Integer> pieceCounts = playerPieceMap.get(player);
        if (pieceCounts == null)
            return;

        Integer count = pieceCounts.get(piece);
        if (count == null || count == 0)
            return;
        if (count == 1) {
            pieceCounts.remove(piece);
        } else {
            pieceCounts.put(piece, count - 1);
        }
    }

    public String toString(Player player) {
        return getPieces(player);
    }
}
