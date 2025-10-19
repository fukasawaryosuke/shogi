package com.shogi.application.service;

import com.shogi.domain.entity.Stand;
import com.shogi.domain.entity.Board;
import com.shogi.domain.entity.piece.Piece;
import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Turn;
import com.shogi.domain.service.Move;
import com.shogi.domain.service.Capture;

public class Game {
  private Board board;
  private Stand stand;
  private Move moveService;
  private Capture captureService;
  private Turn turn;

  public Game() {
    this.board = new Board();
    this.stand = new Stand();
    this.turn = new Turn();

    this.moveService = new Move(this.board, this.turn);
    this.captureService = new Capture(this.stand, this.turn);
  }

  public Turn getTurn() {
    return turn;
  }

  public Board getBoard() {
    return board;
  }

  public Stand getStand() {
    return stand;
  }

  public String play(Position from, Position to) {
    try {
      Piece targetPiece = moveService.movePiece(from, to);
      captureService.capturePiece(targetPiece);
      this.turn.next();
      return null;
    } catch (IllegalArgumentException e) {
      return e.getMessage();
    }
  }

  public boolean isGameOver() {
    return !board.hasOusho(Player.SENTE) || !board.hasOusho(Player.GOTE);
  }
}
