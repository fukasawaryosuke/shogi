package com.shogi.presentation.cli;

public enum ActionType {
  MOVE("1"),
  DROP("2");

  private final String code;

  ActionType(String code) {
    this.code = code;
  }

  public static ActionType fromCode(String code) {
    for (ActionType type : values()) {
      if (type.code.equals(code))
        return type;
    }
    return null;
  }
}
