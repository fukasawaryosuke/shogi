package com.shogi.wasm;

import com.shogi.application.usecase.Game;
import com.shogi.wasm.converter.BoardJsConverter;
import com.shogi.wasm.converter.StandJsConverter;

import org.teavm.interop.Export;

public class ShogiWasm {

  private static Game game;

  @Export(name = "main")
  public static void main(String[] args) {
    game = new Game();
  }

  @Export(name = "getTurn")
  public static int getTurn() {
    return StringBuffer.writeString(game.getTurn().toString());
  }

  @Export(name = "getBoard")
  public static int getBoard() {
    String boardJson = BoardJsConverter.toJson(game.getBoard());
    return StringBuffer.writeString(boardJson);
  }

  @Export(name = "getStand")
  public static int getStand() {
    String standJson = StandJsConverter.toJson(game.getStand());
    return StringBuffer.writeString(standJson);
  }
}
