package com.shogi.domain.service;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.piece.OuSho;
import com.shogi.domain.valueobject.piece.KeiMa;
import com.shogi.domain.valueobject.piece.Piece;
import com.shogi.domain.entity.Board;

/**
 * 王手と詰みの判定を行うサービス
 */
public class CheckMate {
  private final Board board;

  public CheckMate(Board board) {
    this.board = board;
  }

  /**
   * 指定されたプレイヤーが王手されているかチェック
   */
  public boolean isInCheck(Player player) {
    Position ouPosition = findOuSho(player);
    if (ouPosition == null) {
      return false;
    }
    return isPositionUnderAttack(ouPosition, player);
  }

  /**
   * 指定されたプレイヤーが詰んでいるかチェック
   */
  public boolean isCheckmate(Player player) {
    // 王手されていない場合は詰みではない
    if (!isInCheck(player)) {
      return false;
    }

    Position ouPosition = findOuSho(player);
    if (ouPosition == null) {
      return false;
    }

    // 1. 王が逃げられるかチェック
    if (canKingEscape(ouPosition, player)) {
      return false;
    }

    // 2. 王手している駒を取れるかチェック
    if (canCaptureAttacker(ouPosition, player)) {
      return false;
    }

    // 3. 王手している駒との間に駒を置けるかチェック
    if (canBlockAttack(ouPosition, player)) {
      return false;
    }

    return true;
  }

  /**
   * 指定されたプレイヤーの王将の位置を探す
   */
  public Position findOuSho(Player player) {
    for (int y = 1; y <= 9; y++) {
      for (int x = 1; x <= 9; x++) {
        Position pos = new Position(x, y);
        if (board.hasPiece(pos)) {
          Piece piece = board.getPiece(pos);
          if (piece instanceof OuSho && piece.isOwner(player)) {
            return pos;
          }
        }
      }
    }
    return null;
  }

  /**
   * 指定位置が攻撃されているかチェック
   */
  public boolean isPositionUnderAttack(Position position, Player defender) {
    Player attacker = defender.reverse();

    for (int y = 1; y <= 9; y++) {
      for (int x = 1; x <= 9; x++) {
        Position pos = new Position(x, y);
        if (board.hasPiece(pos)) {
          Piece piece = board.getPiece(pos);
          if (piece.isOwner(attacker)) {
            if (piece.canMove(pos, position)) {
              // 桂馬以外は経路チェック
              if (!(piece instanceof KeiMa) && !board.isPathClear(pos, position)) {
                continue;
              }
              return true;
            }
          }
        }
      }
    }
    return false;
  }

  /**
   * 王が逃げられるかチェック
   */
  private boolean canKingEscape(Position ouPosition, Player player) {
    Piece ou = board.getPiece(ouPosition);

    // 8方向すべてをチェック
    for (int dy = -1; dy <= 1; dy++) {
      for (int dx = -1; dx <= 1; dx++) {
        if (dy == 0 && dx == 0)
          continue;

        int newX = ouPosition.getX() + dx;
        int newY = ouPosition.getY() + dy;

        if (newX < 1 || newX > 9 || newY < 1 || newY > 9)
          continue;

        Position escapeTo = new Position(newX, newY);

        // 移動先に自分の駒がある場合はスキップ
        if (board.hasPiece(escapeTo) && board.getPiece(escapeTo).isOwner(player)) {
          continue;
        }

        // 王がその位置に移動できるかチェック
        if (!ou.canMove(ouPosition, escapeTo)) {
          continue;
        }

        // 一時的に王を移動
        Piece captured = board.getPiece(escapeTo);
        board.removePiece(ouPosition);
        board.putPiece(escapeTo, ou);

        // 移動先で王手されていないかチェック
        boolean stillInCheck = isPositionUnderAttack(escapeTo, player);

        // 元に戻す
        board.putPiece(ouPosition, ou);
        if (captured != null) {
          board.putPiece(escapeTo, captured);
        } else {
          board.removePiece(escapeTo);
        }

        if (!stillInCheck) {
          return true; // 逃げられる場所がある
        }
      }
    }

    return false;
  }

  /**
   * 王手している駒を取れるかチェック
   */
  private boolean canCaptureAttacker(Position ouPosition, Player player) {
    Player opponent = player.reverse();

    // 王手している駒を探す
    for (int ay = 1; ay <= 9; ay++) {
      for (int ax = 1; ax <= 9; ax++) {
        Position attackerPos = new Position(ax, ay);
        if (board.hasPiece(attackerPos)) {
          Piece attacker = board.getPiece(attackerPos);
          if (attacker.isOwner(opponent) && attacker.canMove(attackerPos, ouPosition)) {
            // 桂馬以外は経路チェック
            if (!(attacker instanceof KeiMa) && !board.isPathClear(attackerPos, ouPosition)) {
              continue;
            }

            // この駒が王手している
            // 味方の駒でこの駒を取れるかチェック
            for (int dy = 1; dy <= 9; dy++) {
              for (int dx = 1; dx <= 9; dx++) {
                Position defenderPos = new Position(dx, dy);
                if (board.hasPiece(defenderPos)) {
                  Piece defender = board.getPiece(defenderPos);
                  if (defender.isOwner(player) && !(defender instanceof OuSho)) {
                    if (defender.canMove(defenderPos, attackerPos)) {
                      // 桂馬以外は経路チェック
                      if (!(defender instanceof KeiMa) && !board.isPathClear(defenderPos, attackerPos)) {
                        continue;
                      }

                      // 一時的に駒を動かしてみる
                      board.removePiece(defenderPos);
                      board.putPiece(attackerPos, defender);

                      // この状態で王手されていないかチェック
                      boolean stillInCheck = isPositionUnderAttack(ouPosition, player);

                      // 元に戻す
                      board.putPiece(defenderPos, defender);
                      board.putPiece(attackerPos, attacker);

                      if (!stillInCheck) {
                        return true; // 攻撃者を取れる
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    return false;
  }

  /**
   * 王手している駒との間に駒を置けるかチェック
   */
  private boolean canBlockAttack(Position ouPosition, Player player) {
    Player opponent = player.reverse();

    // 王手している駒を探す
    for (int ay = 1; ay <= 9; ay++) {
      for (int ax = 1; ax <= 9; ax++) {
        Position attackerPos = new Position(ax, ay);
        if (board.hasPiece(attackerPos)) {
          Piece attacker = board.getPiece(attackerPos);
          if (attacker.isOwner(opponent) && attacker.canMove(attackerPos, ouPosition)) {
            // 桂馬以外は経路チェック
            if (!(attacker instanceof KeiMa) && !board.isPathClear(attackerPos, ouPosition)) {
              continue;
            }

            // 桂馬または隣接している場合は合駒できない
            if (attacker instanceof KeiMa || isAdjacent(attackerPos, ouPosition)) {
              continue;
            }

            // 攻撃者と王の間の各マスに駒を置けるかチェック
            int xStep = Integer.compare(ouPosition.getX(), attackerPos.getX());
            int yStep = Integer.compare(ouPosition.getY(), attackerPos.getY());
            int currentX = attackerPos.getX() + xStep;
            int currentY = attackerPos.getY() + yStep;

            while (currentX != ouPosition.getX() || currentY != ouPosition.getY()) {
              Position blockPos = new Position(currentX, currentY);

              // 味方の駒でこの位置に移動できるかチェック
              for (int dy = 1; dy <= 9; dy++) {
                for (int dx = 1; dx <= 9; dx++) {
                  Position defenderPos = new Position(dx, dy);
                  if (board.hasPiece(defenderPos)) {
                    Piece defender = board.getPiece(defenderPos);
                    if (defender.isOwner(player) && !(defender instanceof OuSho)) {
                      if (defender.canMove(defenderPos, blockPos)) {
                        // 桂馬以外は経路チェック
                        if (!(defender instanceof KeiMa) && !board.isPathClear(defenderPos, blockPos)) {
                          continue;
                        }

                        // 一時的に駒を動かしてみる
                        board.removePiece(defenderPos);
                        board.putPiece(blockPos, defender);

                        // この状態で王手されていないかチェック
                        boolean stillInCheck = isPositionUnderAttack(ouPosition, player);

                        // 元に戻す
                        board.putPiece(defenderPos, defender);
                        board.removePiece(blockPos);

                        if (!stillInCheck) {
                          return true; // 合駒できる
                        }
                      }
                    }
                  }
                }
              }

              currentX += xStep;
              currentY += yStep;
            }
          }
        }
      }
    }

    return false;
  }

  /**
   * 2つの位置が隣接しているかチェック
   */
  private boolean isAdjacent(Position pos1, Position pos2) {
    int xDiff = Math.abs(pos1.getX() - pos2.getX());
    int yDiff = Math.abs(pos1.getY() - pos2.getY());
    return xDiff <= 1 && yDiff <= 1 && !(xDiff == 0 && yDiff == 0);
  }
}
