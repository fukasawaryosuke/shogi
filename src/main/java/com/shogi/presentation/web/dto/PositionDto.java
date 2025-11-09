package com.shogi.presentation.web.dto;

public class PositionDto {
  private final int x;
  private final int y;

  public PositionDto(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
