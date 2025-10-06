package com.shogi.domain.entity;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;
import com.shogi.domain.entity.piece.*;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private Map<Position, PieceInterface> board;
    private Player currentPlayer;

    public Board() {
        board = new HashMap<>();
        currentPlayer = Player.SENTE;
        initializeBoard();
    }

    private void initializeBoard() {
        board.put(new Position(1, 9), new Kyosha(Player.GOTE));
        board.put(new Position(1, 8), new Keima(Player.GOTE));
        board.put(new Position(1, 7), new Ginsho(Player.GOTE));
        board.put(new Position(1, 6), new Kinsho(Player.GOTE));
        board.put(new Position(1, 5), new Ousho(Player.GOTE));
        board.put(new Position(1, 4), new Kinsho(Player.GOTE));
        board.put(new Position(1, 3), new Ginsho(Player.GOTE));
        board.put(new Position(1, 2), new Keima(Player.GOTE));
        board.put(new Position(1, 1), new Kyosha(Player.GOTE));
        board.put(new Position(2, 2), new Kaku(Player.GOTE));
        board.put(new Position(2, 8), new Hisha(Player.GOTE));
        for (int col = 1; col <= 9; col++) {
            board.put(new Position(3, col), new Fu(Player.GOTE));
            board.put(new Position(7, col), new Fu(Player.SENTE));
        }
        board.put(new Position(8, 8), new Kaku(Player.SENTE));
        board.put(new Position(8, 2), new Hisha(Player.SENTE));
        board.put(new Position(9, 9), new Kyosha(Player.SENTE));
        board.put(new Position(9, 8), new Keima(Player.SENTE));
        board.put(new Position(9, 7), new Ginsho(Player.SENTE));
        board.put(new Position(9, 6), new Kinsho(Player.SENTE));
        board.put(new Position(9, 5), new Ousho(Player.SENTE));
        board.put(new Position(9, 4), new Kinsho(Player.SENTE));
        board.put(new Position(9, 3), new Ginsho(Player.SENTE));
        board.put(new Position(9, 2), new Keima(Player.SENTE));
        board.put(new Position(9, 1), new Kyosha(Player.SENTE));
    }

    public void display(){
        // TODO:フロントエンドに移す
        System.out.println(currentPlayer.toString() + "の番");
        for(int row=1; row<=9; row++){
            for(int col=1; col<=9; col++){
                Position pos = new Position(row, col);
                // 駒がある場合はその駒を表示、ない場合は空白を表示
                if(this.hasPiece(pos)){
                    System.out.print(this.getPiece(pos).getDisplayName());
                } else {
                    System.out.print("　");
                }
            }
            System.out.println();
        }
    }

    // NOTE:ドメインサービスに移すべきかも
    public void movePiece(Position from, Position to) {
        PieceInterface piece = this.getPiece(from);
        if (!piece.canMove(from, to)) {
            throw new IllegalArgumentException("その位置には移動できません。");
        }
        this.resetPiece(from);
        this.setPiece(to, piece);
    }

    private PieceInterface getPiece(Position position) {
        if (!this.hasPiece(position)) {
            throw new IllegalArgumentException("指定された位置に駒がありません。");
        }
        return board.get(position);
    }

    private void setPiece(Position position, PieceInterface piece) {
        if (this.hasPiece(position)) {
            // TODO:相手のコマなら取る
            // TODO:自分のコマなら例外
            // TODO:飛車と角、香車の場合は途中のマスに駒があるかチェック
            throw new IllegalArgumentException("指定された位置には既に駒があります。");
        }
        // TODO:成る処理
        board.put(position, piece);
    }

    private void resetPiece(Position position) {
        if (!this.hasPiece(position)) return;
        board.remove(position);
    }

    private boolean hasPiece(Position position) {
        return board.containsKey(position);
    }
}
