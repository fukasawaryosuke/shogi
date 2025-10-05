package com.shogi.domain.entity;

import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;
import com.shogi.domain.entity.piece.*;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private Map<Position, PieceInterface> board;

    public Board() {
        board = new HashMap<>();
        initializeBoard();
    }

    private void initializeBoard() {
        board.put(new Position(1, 9), new Kyosha(new Position(1, 9),  Player.SENTE));
        board.put(new Position(1, 8), new Keima(new Position(1, 8), Player.SENTE));
        board.put(new Position(1, 7), new Ginsho(new Position(1, 7), Player.SENTE));
        board.put(new Position(1, 6), new Kinsho(new Position(1, 6), Player.SENTE));
        board.put(new Position(1, 5), new Ousho(new Position(1, 5), Player.SENTE));
        board.put(new Position(1, 4), new Kinsho(new Position(1, 4), Player.SENTE));
        board.put(new Position(1, 3), new Ginsho(new Position(1, 3), Player.SENTE));
        board.put(new Position(1, 2), new Keima(new Position(1, 2), Player.SENTE));
        board.put(new Position(1, 1), new Kyosha(new Position(1, 1), Player.SENTE));
        board.put(new Position(2, 2), new Kaku(new Position(1, 1), Player.SENTE));
        board.put(new Position(2, 8), new Hisha(new Position(1, 8), Player.SENTE));
        for (int col = 1; col <= 9; col++) {
            board.put(new Position(3, col), new Fu(new Position(3, col), Player.SENTE));
            board.put(new Position(7, col), new Fu(new Position(7, col), Player.GOTE));
        }
        board.put(new Position(8, 8), new Kaku(new Position(8, 8), Player.GOTE));
        board.put(new Position(8, 2), new Hisha(new Position(8, 2), Player.GOTE));
        board.put(new Position(9, 9), new Kyosha(new Position(9, 9), Player.GOTE));
        board.put(new Position(9, 8), new Keima(new Position(9, 8), Player.GOTE));
        board.put(new Position(9, 7), new Ginsho(new Position(9, 7), Player.GOTE));
        board.put(new Position(9, 6), new Kinsho(new Position(9, 6), Player.GOTE));
        board.put(new Position(9, 5), new Ousho(new Position(9, 5), Player.GOTE));
        board.put(new Position(9, 4), new Kinsho(new Position(9, 4), Player.GOTE));
        board.put(new Position(9, 3), new Ginsho(new Position(9, 3), Player.GOTE));
        board.put(new Position(9, 2), new Keima(new Position(9, 2), Player.GOTE));
        board.put(new Position(9, 1), new Kyosha(new Position(9, 1), Player.GOTE));
    }

    public void display(){
        for(int row=1; row<=9; row++){
            for(int col=1; col<=9; col++){
                Position pos = new Position(row, col);
                // 駒がある場合はその駒を表示、ない場合は空白を表示
                if(board.containsKey(pos)){
                    System.out.print(this.board.get(pos).getDisplayName());
                } else {
                    System.out.print("　");
                }
            }
            System.out.println();
        }
    }
}
