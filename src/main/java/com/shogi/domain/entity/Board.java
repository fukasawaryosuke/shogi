package com.shogi.domain.entity;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.entity.piece.*;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private Map<Position, Piece> pieceMap;

    public Board() {
        this.pieceMap = new HashMap<>();
        initializeBoard();
    }

    private void initializeBoard() {
        // 駒の初期配置
        // GOTE側
        // ・ 1 2 3 4 5 6 7 8 9
        // 1 香 桂 銀 金 王 金 銀 桂 香
        // 2 ・ 飛 ・ ・ ・ ・ ・ 角 ・
        // 3 歩 歩 歩 歩 歩 歩 歩 歩 歩
        // 4 ・ ・ ・ ・ ・ ・ ・ ・ ・
        // 5 ・ ・ ・ ・ ・ ・ ・ ・ ・
        // 6 ・ ・ ・ ・ ・ ・ ・ ・ ・
        // 7 歩 歩 歩 歩 歩 歩 歩 歩 歩
        // 8 ・ 角 ・ ・ ・ ・ ・ 飛 ・
        // 9 香 桂 銀 金 王 金 銀 桂 香
        // SENTE側

        this.pieceMap.put(new Position(1, 1), new KyoSha(Player.GOTE));
        this.pieceMap.put(new Position(2, 1), new KeiMa(Player.GOTE));
        this.pieceMap.put(new Position(3, 1), new GinSho(Player.GOTE));
        this.pieceMap.put(new Position(4, 1), new KinSho(Player.GOTE));
        this.pieceMap.put(new Position(5, 1), new OuSho(Player.GOTE));
        this.pieceMap.put(new Position(6, 1), new KinSho(Player.GOTE));
        this.pieceMap.put(new Position(7, 1), new GinSho(Player.GOTE));
        this.pieceMap.put(new Position(8, 1), new KeiMa(Player.GOTE));
        this.pieceMap.put(new Position(9, 1), new KyoSha(Player.GOTE));
        this.pieceMap.put(new Position(2, 2), new HiSha(Player.GOTE));
        this.pieceMap.put(new Position(8, 2), new KakuGyou(Player.GOTE));
        for (int col = 1; col <= 9; col++) {
            this.pieceMap.put(new Position(col, 3), new FuHyo(Player.GOTE));
            this.pieceMap.put(new Position(col, 7), new FuHyo(Player.SENTE));
        }
        this.pieceMap.put(new Position(2, 8), new KakuGyou(Player.SENTE));
        this.pieceMap.put(new Position(8, 8), new HiSha(Player.SENTE));
        this.pieceMap.put(new Position(1, 9), new KyoSha(Player.SENTE));
        this.pieceMap.put(new Position(2, 9), new KeiMa(Player.SENTE));
        this.pieceMap.put(new Position(3, 9), new GinSho(Player.SENTE));
        this.pieceMap.put(new Position(4, 9), new KinSho(Player.SENTE));
        this.pieceMap.put(new Position(5, 9), new OuSho(Player.SENTE));
        this.pieceMap.put(new Position(6, 9), new KinSho(Player.SENTE));
        this.pieceMap.put(new Position(7, 9), new GinSho(Player.SENTE));
        this.pieceMap.put(new Position(8, 9), new KeiMa(Player.SENTE));
        this.pieceMap.put(new Position(9, 9), new KyoSha(Player.SENTE));
    }

    public boolean hasPiece(Position position) {
        return this.pieceMap.containsKey(position);
    }

    public Piece getPiece(Position position) {
        return this.pieceMap.get(position);
    }

    public void putPiece(Position position, Piece piece) {
        pieceMap.put(position, piece);
    }

    public void removePiece(Position position) {
        pieceMap.remove(position);
    }

    public boolean hasOusho(Player player) {
        for (Piece piece : pieceMap.values()) {
            if (piece instanceof OuSho && piece.isOwner(player)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEnemyZone(Position position, Player player) {
        int row = position.getRow();
        if (player == Player.SENTE)
            return row <= 3;
        if (player == Player.GOTE)
            return row >= 7;
        return false;
    }

    public boolean isEnemyZoneOneRow(Position position, Player player) {
        int row = position.getRow();
        if (player == Player.SENTE)
            return row == 1;
        if (player == Player.GOTE)
            return row == 9;
        return false;
    }

    public boolean isPathClear(Position from, Position to) {
        int fromCol = from.getCol();
        int fromRow = from.getRow();
        int toCol = to.getCol();
        int toRow = to.getRow();

        int colStep = Integer.compare(toCol, fromCol);
        int rowStep = Integer.compare(toRow, fromRow);
        int currentCol = fromCol + colStep;
        int currentRow = fromRow + rowStep;

        while (currentCol != toCol || currentRow != toRow) {
            Position currentPosition = new Position(currentCol, currentRow);
            if (this.hasPiece(currentPosition)) {
                return false;
            }
            currentCol += colStep;
            currentRow += rowStep;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 1; row <= 9; row++) {
            for (int col = 1; col <= 9; col++) {
                Position pos = new Position(col, row);
                if (this.hasPiece(pos)) {
                    sb.append(this.getPiece(pos).toString());
                } else {
                    sb.append("　");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
