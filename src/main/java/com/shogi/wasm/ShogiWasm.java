package com.shogi.wasm;

import com.shogi.application.usecase.Game;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.piece.PieceType;
import com.shogi.wasm.converter.BoardJsConverter;
import com.shogi.wasm.converter.StandJsConverter;

import org.teavm.interop.Export;

public class ShogiWasm {

  private static Game game;

  /**
   * ゲームを初期化する
   */
  @Export(name = "main")
  public static void main(String[] args) {
    game = new Game();
  }

  /**
   * 現在の手番を取得する
   *
   * @return 手番の文字列のバイト数
   */
  @Export(name = "getTurn")
  public static int getTurn() {
    return StringBuffer.writeString(game.getTurn().toString());
  }

  /**
   * 盤面を取得する
   *
   * @return 盤面のJSON文字列のバイト数
   */
  @Export(name = "getBoard")
  public static int getBoard() {
    String boardJson = BoardJsConverter.toJson(game.getBoard());
    return StringBuffer.writeString(boardJson);
  }

  /**
   * 持ち駒を取得する
   *
   * @return 持ち駒のJSON文字列のバイト数
   */
  @Export(name = "getStand")
  public static int getStand() {
    String standJson = StandJsConverter.toJson(game.getStand());
    return StringBuffer.writeString(standJson);
  }

  /**
   * 駒を移動する
   *
   * @param fromX 移動元X座標
   * @param fromY 移動元Y座標
   * @param toX   移動先X座標
   * @param toY   移動先Y座標
   * @return エラーメッセージのバイト数（成功時は0、エラー時はメッセージの長さ）
   */
  @Export(name = "move")
  public static int move(int fromX, int fromY, int toX, int toY) {
    try {
      Position from = new Position(fromX, fromY);
      Position to = new Position(toX, toY);

      String error = game.move(from, to);

      if (error == null) {
        // 成功時は空文字列を返す
        return StringBuffer.writeString("");
      } else {
        // エラー時はエラーメッセージを返す
        return StringBuffer.writeString(error);
      }
    } catch (Exception e) {
      return StringBuffer.writeString("Error: " + e.getMessage());
    }
  }

  /**
   * 持ち駒を打つ
   *
   * @param pieceTypeNameLength 駒の種類名のバイト長
   * @param x                   X座標
   * @param y                   Y座標
   * @return エラーメッセージのバイト数（成功時は0）
   */
  @Export(name = "drop")
  public static int drop(int pieceTypeNameLength, int x, int y) {
    try {
      // バッファから文字列を読み取る
      String pieceTypeName = StringBuffer.readString(pieceTypeNameLength);

      if (pieceTypeName == null || pieceTypeName.isEmpty()) {
        return StringBuffer.writeString("駒の種類が指定されていません");
      }

      PieceType pieceType = PieceType.valueOf(pieceTypeName);
      Position position = new Position(x, y);

      String error = game.drop(pieceType, position);

      if (error == null) {
        return 0; // 成功
      }

      return StringBuffer.writeString(error);
    } catch (Exception e) {
      return StringBuffer.writeString("駒の配置に失敗しました: " + e.getMessage());
    }
  }

  /**
   * 次のターンに進む
   */
  @Export(name = "nextTurn")
  public static void nextTurn() {
    game.nextTurn();
  }
}
