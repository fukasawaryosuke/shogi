package com.shogi.presentation.web.mapper;

import com.shogi.domain.entity.Board;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.piece.Piece;
import com.shogi.presentation.web.dto.BoardDto;
import com.shogi.presentation.web.dto.PieceDto;
import com.shogi.presentation.web.dto.PositionDto;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class BoardMapper {
  public static BoardDto toDto(Board boardDomain) {
    Map<Position, Piece> boardMap = boardDomain.getBoardMap();

    List<PieceDto> pieceDtos = new ArrayList<>();
    boardMap.forEach((position, piece) -> {
      PositionDto positionDto = new PositionDto(position.getX(), position.getY());
      PieceDto pieceDto = new PieceDto(positionDto, piece.toString(), piece.getOwner());
      pieceDtos.add(pieceDto);
    });
    return new BoardDto(pieceDtos);
  }
}
