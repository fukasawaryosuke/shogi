package com.shogi.presentation.web.mapper;

import com.shogi.domain.entity.Stand;
import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.piece.Piece;
import com.shogi.presentation.web.dto.StandDto;

import java.util.HashMap;
import java.util.Map;

public class StandMapper {
  public static StandDto toDto(Stand standDomain) {
    Map<Player, Map<Piece, Integer>> standMap = standDomain.getStandMap();
    Map<String, String> result = new HashMap<>();
    standMap.forEach((player, pieces) -> {
      pieces.forEach((piece, count) -> result.put(player.toString() + ":" + piece.toString(), count.toString()));
    });
    return new StandDto(result);
  }
}
