package com.shogi.presentation.web.mapper;

import com.shogi.domain.entity.Board;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.piece.Piece;
import com.shogi.presentation.web.dto.BoardDto;

import java.util.HashMap;
import java.util.Map;

public class BoardMapper {
  public static BoardDto toDto(Board boardDomain) {
    Map<Position, Piece> boardMap = boardDomain.getBoardMap();
    Map<String, String> result = new HashMap<>();
    boardMap.forEach((pos, piece) -> result.put(pos.toString(), piece.toString()));
    return new BoardDto(result);
  }
}
