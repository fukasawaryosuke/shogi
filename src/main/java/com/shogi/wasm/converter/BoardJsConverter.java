package com.shogi.wasm.converter;

import com.shogi.domain.entity.Board;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.piece.Piece;

import java.util.Map;

/**
 * Board情報をJSON文字列に変換するコンバーター
 */
public class BoardJsConverter {

  /**
   * BoardオブジェクトをJSON文字列に変換
   *
   * @param board Boardオブジェクト
   * @return JSON文字列
   */
  public static String toJson(Board board) {
    Map<Position, Piece> boardMap = board.getBoardMap();
    StringBuilder json = new StringBuilder(3000);
    json.append("[");

    for (int y = 1; y <= 9; y++) {
      json.append("[");
      for (int x = 1; x <= 9; x++) {
        Position pos = new Position(x, y);
        Piece piece = boardMap.get(pos);

        if (piece != null) {
          json.append("{\"position\":{\"x\":")
              .append(x)
              .append(",\"y\":")
              .append(y)
              .append("},\"piece\":{\"name\":\"")
              .append(escapeJson(piece.toString()))
              .append("\",\"owner\":\"")
              .append(piece.getOwner())
              .append("\"}}");
        } else {
          json.append("null");
        }

        if (x < 9) {
          json.append(",");
        }
      }
      json.append("]");
      if (y < 9) {
        json.append(",");
      }
    }

    json.append("]");
    return json.toString();
  }

  /**
   * JSON文字列内の特殊文字をエスケープ
   */
  private static String escapeJson(String str) {
    if (str == null) {
      return "";
    }
    return str.replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t");
  }
}
