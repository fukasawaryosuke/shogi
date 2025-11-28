package com.shogi.wasm.converter;

import com.shogi.domain.entity.Stand;
import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.piece.Piece;

import java.util.Map;

/**
 * Stand情報をJSON文字列に変換するコンバーター
 */
public class StandJsConverter {

  /**
   * StandオブジェクトをJSON文字列に変換
   * 形式: {"player": [{"name": "歩", "type": "FU", "count": 2}]}
   *
   * @param stand Standオブジェクト
   * @return JSON文字列
   */
  public static String toJson(Stand stand) {
    Map<Player, Map<Piece, Integer>> standMap = stand.getStandMap();
    StringBuilder json = new StringBuilder(500);
    json.append("{");

    boolean firstPlayer = true;
    for (Map.Entry<Player, Map<Piece, Integer>> playerEntry : standMap.entrySet()) {
      if (!firstPlayer) {
        json.append(",");
      }
      firstPlayer = false;

      Player player = playerEntry.getKey();
      Map<Piece, Integer> pieces = playerEntry.getValue();

      json.append("\"").append(player.toString()).append("\":[");

      boolean firstPiece = true;
      for (Map.Entry<Piece, Integer> pieceEntry : pieces.entrySet()) {
        if (!firstPiece) {
          json.append(",");
        }
        firstPiece = false;

        Piece piece = pieceEntry.getKey();
        Integer count = pieceEntry.getValue();

        json.append("{\"name\":\"")
            .append(escapeJson(piece.toString()))
            .append("\",\"type\":\"")
            .append(piece.getType().name())
            .append("\",\"count\":")
            .append(count)
            .append("}");
      }

      json.append("]");
    }

    json.append("}");
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
