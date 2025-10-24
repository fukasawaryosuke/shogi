package com.shogi.domain.service;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Turn;
import com.shogi.domain.entity.Board;
import com.shogi.domain.entity.Stand;
import com.shogi.domain.entity.piece.Piece;

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
    Player player = this.turn.getCurrentPlayer();

    this.validateDrop(piece, position, player);
    this.board.putPiece(position, piece);
    stand.removePiece(player, piece);

    return true;
  }

  private void validateDrop(Piece piece, Position position, Player player) {
    if (!stand.hasPiece(player, piece)) {
      throw new IllegalArgumentException(piece + "は持ち駒に存在しません");
    }

    if (board.hasPiece(position)) {
      throw new IllegalArgumentException(position + "には既に駒が存在します");
    }
  }
}
