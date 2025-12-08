package com.shogi.domain.service;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Turn;
import com.shogi.domain.valueobject.piece.FuHyo;
import com.shogi.domain.valueobject.piece.Piece;
import com.shogi.domain.valueobject.piece.PieceType;
import com.shogi.domain.entity.Board;
import com.shogi.domain.entity.Stand;
import com.shogi.domain.factory.PieceFactory;

public class Drop {
  private final Board board;
  private final Stand stand;
  private final Turn turn;
  private final CheckMate checkMateService;

  public Drop(Board board, Stand stand, Turn turn) {
    this.board = board;
    this.stand = stand;
    this.turn = turn;
    this.checkMateService = new CheckMate(board);
  }

  public boolean dropPiece(PieceType pieceType, Position position) {
    Player player = this.turn.getCurrentPlayer();
    Piece dropPiece = PieceFactory.createPiece(pieceType, player);

    this.validateDrop(dropPiece, position, player);
    this.board.putPiece(position, dropPiece);
    stand.removePiece(player, dropPiece);

    return true;
  }

  private void validateDrop(Piece piece, Position position, Player player) {
    if (!stand.hasPiece(player, piece))
      throw new IllegalArgumentException(piece + "は持ち駒に存在しません");

    if (board.hasPiece(position))
      throw new IllegalArgumentException(position + "には既に駒が存在します");

    // 二歩のチェック（歩のみ、と金は除外）
    if (piece instanceof FuHyo) {
      if (hasDoubleHyo(position.getX(), player)) {
        throw new IllegalArgumentException("二歩は禁止されています");
      }

      // 打ち歩詰めのチェック
      if (isUchifuZume(piece, position, player)) {
        throw new IllegalArgumentException("打ち歩詰めは禁止されています");
      }
    }
  }

  private boolean hasDoubleHyo(int x, Player player) {
    for (int y = 1; y <= 9; y++) {
      Position pos = new Position(x, y);
      if (board.hasPiece(pos)) {
        Piece piece = board.getPiece(pos);
        if (piece instanceof FuHyo && piece.isOwner(player)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 打ち歩詰めかどうかをチェック
   * 歩を打った位置が相手の王に王手をかけ、かつ相手が詰んでいる場合は打ち歩詰め
   */
  private boolean isUchifuZume(Piece fu, Position fuPosition, Player player) {
    // 一時的に歩を配置
    board.putPiece(fuPosition, fu);

    try {
      Player opponent = player.reverse();

      // 相手の王の位置を探す
      Position ouPosition = checkMateService.findOuSho(opponent);
      if (ouPosition == null) {
        return false;
      }

      // この歩が王手をかけているかチェック
      if (!fu.canMove(fuPosition, ouPosition)) {
        return false;
      }

      // 王手をかけている場合、相手が詰んでいるかチェック
      boolean isMate = checkMateService.isCheckmate(opponent);

      return isMate;
    } finally {
      // 一時的に配置した歩を削除
      board.removePiece(fuPosition);
    }
  }
}
