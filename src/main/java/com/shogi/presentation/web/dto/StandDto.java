package com.shogi.presentation.web.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

public class StandDto {
  private final Map<String, String> stand;

  public StandDto(Map<String, String> stand) {
    this.stand = stand;
  }

  @JsonValue
  public Map<String, String> getStand() {
    return stand;
  }
}
