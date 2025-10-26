package com.shogi.application.usecase;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.PieceType;
import com.shogi.domain.valueobject.Turn;
import com.shogi.domain.entity.Stand;
import com.shogi.domain.entity.Board;
import com.shogi.domain.entity.piece.Piece;
import com.shogi.domain.service.Capture;
import com.shogi.domain.service.Move;
import com.shogi.domain.service.Drop;
import com.shogi.domain.service.Promote;

public class Game {
  private final Board board;
  private final Stand stand;
  private final Turn turn;

  private final Move moveService;
  private final Capture captureService;
  private final Drop dropService;
  private final Promote promoteService;

  public Game() {
    this.board = new Board();
    this.stand = new Stand();
    this.turn = new Turn();

    this.moveService = new Move(this.board, this.turn);
    this.captureService = new Capture(this.stand, this.turn);
    this.dropService = new Drop(this.board, this.stand, this.turn);
    this.promoteService = new Promote(this.board, this.turn);
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

  public void nextTurn() {
    this.turn.next();
  }

  public String move(Position from, Position to) {
    try {
      Piece originPiece = this.board.getPiece(from);
      Piece targetPiece = this.board.getPiece(to);

      moveService.movePiece(originPiece, targetPiece, from, to);
      captureService.capturePiece(targetPiece);

      return null;
    } catch (IllegalArgumentException e) {
      return e.getMessage();
    }
  }

  public void promote(Position to) {
    promoteService.promotePiece(to);
  }

  public boolean canChoosePromote(Position to) {
    return promoteService.canChoosePromote(to);
  }

  public boolean mustPromote(Position to) {
    return promoteService.mustPromote(to);
  }

  public String drop(PieceType pieceType, Position position) {
    try {
      dropService.dropPiece(pieceType, position);

      return null;
    } catch (IllegalArgumentException e) {
      return e.getMessage();
    }
  }

  public boolean isGameOver() {
    return !board.hasOusho(this.turn.getCurrentPlayer());
  }
}
