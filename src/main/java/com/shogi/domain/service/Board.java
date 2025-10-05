package com.shogi.domain.service;

import com.shogi.domain.entity.Piece;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Turn;

public class Board {
    private Piece[][] squares;  // 9x9の盤面
    private static final int SIZE = 9;

    // コンストラクタ
    public Board() {
        squares = new Piece[SIZE][SIZE];
        initializeBoard();
    }

    // 盤面の初期化
    private void initializeBoard() {
        // 後手の駒（上段）
        squares[0][0] = new Piece("香", Turn.GOTE, new Position(1, 1));
        squares[0][1] = new Piece("桂", Turn.GOTE, new Position(1, 2));
        squares[0][2] = new Piece("銀", Turn.GOTE, new Position(1, 3));
        squares[0][3] = new Piece("金", Turn.GOTE, new Position(1, 4));
        squares[0][4] = new Piece("王", Turn.GOTE, new Position(1, 5));
        squares[0][5] = new Piece("金", Turn.GOTE, new Position(1, 6));
        squares[0][6] = new Piece("銀", Turn.GOTE, new Position(1, 7));
        squares[0][7] = new Piece("桂", Turn.GOTE, new Position(1, 8));
        squares[0][8] = new Piece("香", Turn.GOTE, new Position(1, 9));

        // 後手の歩（2段目）
        for (int col = 1; col <= 9; col++) {
            squares[1][col-1] = new Piece("歩", Turn.GOTE, new Position(2, col));
        }

        // 先手の駒（9段目）
        squares[8][0] = new Piece("香", Turn.SENTE, new Position(9, 1));
        squares[8][1] = new Piece("桂", Turn.SENTE, new Position(9, 2));
        squares[8][2] = new Piece("銀", Turn.SENTE, new Position(9, 3));
        squares[8][3] = new Piece("金", Turn.SENTE, new Position(9, 4));
        squares[8][4] = new Piece("王", Turn.SENTE, new Position(9, 5));
        squares[8][5] = new Piece("金", Turn.SENTE, new Position(9, 6));
        squares[8][6] = new Piece("銀", Turn.SENTE, new Position(9, 7));
        squares[8][7] = new Piece("桂", Turn.SENTE, new Position(9, 8));
        squares[8][8] = new Piece("香", Turn.SENTE, new Position(9, 9));

                // 先手の歩（8段目）
        for (int col = 1; col <= 9; col++) {
            squares[7][col-1] = new Piece("歩", Turn.SENTE, new Position(8, col));
        }
    }

    // 指定位置の駒を取得
    public Piece getPieceAt(Position position) {
        if (!position.isValid()) {
            return null;
        }
        return squares[position.getRow()-1][position.getCol()-1];
    }

    // 盤面を表示
    public void display() {
        System.out.println("  1 2 3 4 5 6 7 8 9");
        for (int row = 0; row < SIZE; row++) {
            System.out.print((row + 1) + " ");
            for (int col = 0; col < SIZE; col++) {
                Piece piece = squares[row][col];
                if (piece == null) {
                    System.out.print("・");
                } else {
                    System.out.print(piece.getDisplayName());
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
