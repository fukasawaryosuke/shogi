package com.shogi.wasm;

import com.shogi.application.usecase.Game;

import org.teavm.interop.Export;

public class ShogiWasm {

  private static Game game;

  @Export(name = "main")
  public static void main(String[] args) {
    game = new Game();
  }
}
