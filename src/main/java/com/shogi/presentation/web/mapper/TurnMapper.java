package com.shogi.presentation.web.mapper;

import com.shogi.domain.valueobject.Turn;
import com.shogi.presentation.web.dto.TurnDto;

public class TurnMapper {
  public static TurnDto toDto(Turn turnDomain) {
    return new TurnDto(turnDomain.toString());
  }
}
