package com.shogi.presentation.web.dto;

public class PieceDto {
  private PositionDto position;
  private String name;
  private String owner;

  public PieceDto(PositionDto position, String name, String owner) {
    this.position = position;
    this.name = name;
    this.owner = owner;
  }

  public PositionDto getPosition() {
    return position;
  }

  public String getName() {
    return name;
  }

  public String getOwner() {
    return owner;
  }
}
