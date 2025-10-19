package com.shogi.domain.valueobject;

public class Turn {
  private Player currentPlayer = Player.SENTE;

  public Player getCurrentPlayer() {
    return this.currentPlayer;
  }

  public void next() {
    this.currentPlayer = (this.currentPlayer == Player.SENTE) ? Player.GOTE : Player.SENTE;
  }

  @Override
  public String toString() {
    return this.currentPlayer.toString();
  }
}
