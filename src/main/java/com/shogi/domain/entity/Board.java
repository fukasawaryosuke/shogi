package com.shogi.domain.entity;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.piece.*;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private Map<Position, Piece> boardMap;

    public Board() {
        this.boardMap = new HashMap<>();
        initializeBoard();
    }

    public Map<Position, Piece> getBoardMap() {
        return this.boardMap;
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

        this.boardMap.put(new Position(1, 1), new KyoSha(Player.GOTE));
        this.boardMap.put(new Position(2, 1), new KeiMa(Player.GOTE));
        this.boardMap.put(new Position(3, 1), new GinSho(Player.GOTE));
        this.boardMap.put(new Position(4, 1), new KinSho(Player.GOTE));
        this.boardMap.put(new Position(5, 1), new OuSho(Player.GOTE));
        this.boardMap.put(new Position(6, 1), new KinSho(Player.GOTE));
        this.boardMap.put(new Position(7, 1), new GinSho(Player.GOTE));
        this.boardMap.put(new Position(8, 1), new KeiMa(Player.GOTE));
        this.boardMap.put(new Position(9, 1), new KyoSha(Player.GOTE));
        this.boardMap.put(new Position(2, 2), new HiSha(Player.GOTE));
        this.boardMap.put(new Position(8, 2), new KakuGyou(Player.GOTE));
        for (int col = 1; col <= 9; col++) {
            this.boardMap.put(new Position(col, 3), new FuHyo(Player.GOTE));
            this.boardMap.put(new Position(col, 7), new FuHyo(Player.SENTE));
        }
        this.boardMap.put(new Position(2, 8), new KakuGyou(Player.SENTE));
        this.boardMap.put(new Position(8, 8), new HiSha(Player.SENTE));
        this.boardMap.put(new Position(1, 9), new KyoSha(Player.SENTE));
        this.boardMap.put(new Position(2, 9), new KeiMa(Player.SENTE));
        this.boardMap.put(new Position(3, 9), new GinSho(Player.SENTE));
        this.boardMap.put(new Position(4, 9), new KinSho(Player.SENTE));
        this.boardMap.put(new Position(5, 9), new OuSho(Player.SENTE));
        this.boardMap.put(new Position(6, 9), new KinSho(Player.SENTE));
        this.boardMap.put(new Position(7, 9), new GinSho(Player.SENTE));
        this.boardMap.put(new Position(8, 9), new KeiMa(Player.SENTE));
        this.boardMap.put(new Position(9, 9), new KyoSha(Player.SENTE));
    }

    public boolean hasPiece(Position position) {
        return this.boardMap.containsKey(position);
    }

    public Piece getPiece(Position position) {
        return this.boardMap.get(position);
    }

    public void putPiece(Position position, Piece piece) {
        boardMap.put(position, piece);
    }

    public void removePiece(Position position) {
        boardMap.remove(position);
    }

    public boolean hasOusho(Player player) {
        for (Piece piece : boardMap.values()) {
            if (piece instanceof OuSho && piece.isOwner(player)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEnemyZone(Position position, Player player) {
        int y = position.getY();
        if (player == Player.SENTE)
            return y <= 3;
        if (player == Player.GOTE)
            return y >= 7;
        return false;
    }

    public boolean isEnemyZoneEdge(Position position, Player player) {
        int y = position.getY();
        if (player == Player.SENTE)
            return y == 1;
        if (player == Player.GOTE)
            return y == 9;
        return false;
    }

    public boolean isPathClear(Position from, Position to) {
        int fromX = from.getX();
        int fromY = from.getY();
        int toX = to.getX();
        int toY = to.getY();

        int xStep = Integer.compare(toX, fromX);
        int yStep = Integer.compare(toY, fromY);
        int currentX = fromX + xStep;
        int currentY = fromY + yStep;

        while (currentX != toX || currentY != toY) {
            Position currentPosition = new Position(currentX, currentY);
            if (this.hasPiece(currentPosition)) {
                return false;
            }
            currentX += xStep;
            currentY += yStep;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 1; y <= 9; y++) {
            for (int x = 1; x <= 9; x++) {
                Position pos = new Position(x, y);
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
