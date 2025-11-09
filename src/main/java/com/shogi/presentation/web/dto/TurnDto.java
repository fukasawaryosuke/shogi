package com.shogi.presentation.web.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public class TurnDto {
  private final String turn;

  public TurnDto(String turn) {
    this.turn = turn;
  }

  @JsonValue
  public String getTurn() {
    return turn;
  }
}
