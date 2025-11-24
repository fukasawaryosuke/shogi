package com.shogi.wasm;

import com.shogi.application.usecase.Game;
import org.teavm.interop.Export;
import org.teavm.interop.Import;

public class ShogiWasm {

  private static Game game;

  @Export(name = "main")
  public static void main(String[] args) {
    log("ShogiWasm main started");
    game = new Game();
  }

  @Import(module = "console", name = "log")
  private static native void log(String message);
}
