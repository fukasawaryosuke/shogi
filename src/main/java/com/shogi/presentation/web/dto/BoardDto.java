package com.shogi.presentation.web.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public class BoardDto {
  private List<PieceDto> board;

  public BoardDto(List<PieceDto> board) {
    this.board = board;
  }

  @JsonValue
  public List<PieceDto> getBoard() {
    return board;
  }
}
