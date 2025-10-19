package com.shogi.application.service;

import com.shogi.domain.entity.Stand;
import com.shogi.domain.entity.Board;
import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Turn;
import com.shogi.domain.service.Move;

public class Game {
  private Board board;
  private Stand stand;
  private Move moveService;
  private Turn turn;

  public Game() {
    board = new Board();
    stand = new Stand(Player.SENTE);
    turn = new Turn();
    moveService = new Move(board, stand, turn);
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

  public String movePiece(Position from, Position to) {
    try {
      Player currentPlayer = turn.getCurrentPlayer();
      moveService.movePiece(from, to, currentPlayer);
      return null;
    } catch (IllegalArgumentException e) {
      return e.getMessage();
    }
  }

  public boolean isGameOver() {
    return !board.hasOusho(Player.SENTE) || !board.hasOusho(Player.GOTE);
  }
}
