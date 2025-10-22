package com.shogi.application.service;

import com.shogi.domain.entity.Stand;
import com.shogi.domain.entity.Board;
import com.shogi.domain.entity.piece.Piece;
import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Turn;
import com.shogi.domain.valueobject.PieceType;
import com.shogi.domain.service.Move;
import com.shogi.domain.service.Capture;
import com.shogi.domain.service.Drop;
import com.shogi.domain.factory.PieceFactory;


public class Game {
  private Board board;
  private Stand stand;
  private Turn turn;

  private Move moveService;
  private Capture captureService;
  private Drop dropService;

  public Game() {
    this.board = new Board();
    this.stand = new Stand();
    this.turn = new Turn();

    this.moveService = new Move(this.board, this.turn);
    this.captureService = new Capture(this.stand, this.turn);
    this.dropService = new Drop(this.board, this.stand, this.turn);
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

  public Player getCurrentPlayer() {
    return this.turn.getCurrentPlayer();
  }

  public String drop(PieceType pieceType, Position position) {
    try {
      Player currentPlayer = this.getCurrentPlayer();
      Piece piece = PieceFactory.createPiece(pieceType, currentPlayer);
      dropService.dropPiece(piece, position);
      this.turn.next();
      return null;
    } catch (IllegalArgumentException e) {
      return e.getMessage();
    }
  }

  public String move(Position from, Position to) {
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
