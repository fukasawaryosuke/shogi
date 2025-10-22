package com.shogi.domain.service;

import com.shogi.domain.entity.Board;
import com.shogi.domain.entity.Stand;
import com.shogi.domain.entity.piece.Piece;
import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Turn;
import com.shogi.domain.valueobject.Position;

public class Drop {
  private Board board;
  private Stand stand;
  private Turn turn;

  public Drop(Board board, Stand stand, Turn turn) {
    this.board = board;
    this.stand = stand;
    this.turn = turn;
  }

  public boolean dropPiece(Piece piece, Position position) {
    Player player = turn.getCurrentPlayer();

    this.validateDrop(piece, position);

    this.board.putPiece(position, piece);

    stand.removePiece(player, piece);

    return true;
  }

  private void validateDrop(Piece piece, Position position) {
    Player currentPlayer = turn.getCurrentPlayer();

    if (!stand.hasPiece(currentPlayer, piece)) {
      throw new IllegalArgumentException(piece + "は持ち駒に存在しません");
    }

    if (board.hasPiece(position)) {
      throw new IllegalArgumentException(position + "には既に駒が存在します");
    }
  }
}
