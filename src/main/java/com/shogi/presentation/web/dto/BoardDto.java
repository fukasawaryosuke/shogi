package com.shogi.presentation.web.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

public class BoardDto {
  private final Map<String, String> board;

  public BoardDto(Map<String, String> board) {
    this.board = board;
  }

  @JsonValue
  public Map<String, String> getBoard() {
    return board;
  }
}
