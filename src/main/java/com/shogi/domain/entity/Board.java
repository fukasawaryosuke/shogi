package com.shogi.domain.entity;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;
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

        this.pieceMap.put(new Position(1, 1), new Kyosha(Player.GOTE));
        this.pieceMap.put(new Position(2, 1), new Keima(Player.GOTE));
        this.pieceMap.put(new Position(3, 1), new Ginsho(Player.GOTE));
        this.pieceMap.put(new Position(4, 1), new Kinsho(Player.GOTE));
        this.pieceMap.put(new Position(5, 1), new Ousho(Player.GOTE));
        this.pieceMap.put(new Position(6, 1), new Kinsho(Player.GOTE));
        this.pieceMap.put(new Position(7, 1), new Ginsho(Player.GOTE));
        this.pieceMap.put(new Position(8, 1), new Keima(Player.GOTE));
        this.pieceMap.put(new Position(9, 1), new Kyosha(Player.GOTE));
        this.pieceMap.put(new Position(2, 2), new Hisha(Player.GOTE));
        this.pieceMap.put(new Position(8, 2), new Kaku(Player.GOTE));
        for (int col = 1; col <= 9; col++) {
            this.pieceMap.put(new Position(col, 3), new Fu(Player.GOTE));
            this.pieceMap.put(new Position(col, 7), new Fu(Player.SENTE));
        }
        this.pieceMap.put(new Position(2, 8), new Kaku(Player.SENTE));
        this.pieceMap.put(new Position(8, 8), new Hisha(Player.SENTE));
        this.pieceMap.put(new Position(1, 9), new Kyosha(Player.SENTE));
        this.pieceMap.put(new Position(2, 9), new Keima(Player.SENTE));
        this.pieceMap.put(new Position(3, 9), new Ginsho(Player.SENTE));
        this.pieceMap.put(new Position(4, 9), new Kinsho(Player.SENTE));
        this.pieceMap.put(new Position(5, 9), new Ousho(Player.SENTE));
        this.pieceMap.put(new Position(6, 9), new Kinsho(Player.SENTE));
        this.pieceMap.put(new Position(7, 9), new Ginsho(Player.SENTE));
        this.pieceMap.put(new Position(8, 9), new Keima(Player.SENTE));
        this.pieceMap.put(new Position(9, 9), new Kyosha(Player.SENTE));
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
            if (piece instanceof Ousho && piece.isOwner(player)) {
                return true;
            }
        }
        return false;
    }

    // 移動経路に駒があるかの判定
    public boolean isPathClear(Position from, Position to) {
        // fromとtoの行と列を取得
        int fromCol = from.getCol();
        int fromRow = from.getRow();
        int toCol = to.getCol();
        int toRow = to.getRow();

        // 縦方向の進む向き(上:-1, 下:+1, 同じ:0)
        int colStep = Integer.compare(toCol, fromCol);

        // 横方向の進む向き(左:-1, 右:+1, 同じ:0)
        int rowStep = Integer.compare(toRow, fromRow);

        // 現在の位置をfromの次のマスに設定
        // 例えば、fromが(3,3)でtoが(3,7)の場合、currentは(3,4)からスタート
        int currentCol = fromCol + colStep;
        int currentRow = fromRow + rowStep;

        // toに到達するまでループ
        while (currentCol != toCol || currentRow != toRow) {
            Position currentPosition = new Position(currentCol, currentRow);
            if (this.hasPiece(currentPosition)) {
                return false; // 経路上に駒が存在
            }
            currentCol += colStep;
            currentRow += rowStep;
        }
        return true; // 経路上に駒が存在しない
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
                    sb.append("　"); // 全角スペースで盤面の空きを表現
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
