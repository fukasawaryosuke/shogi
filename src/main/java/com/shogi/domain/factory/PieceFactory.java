package com.shogi.domain.factory;

import com.shogi.domain.entity.piece.Fu;
import com.shogi.domain.entity.piece.Ginsho;
import com.shogi.domain.entity.piece.Hisha;
import com.shogi.domain.entity.piece.Kaku;
import com.shogi.domain.entity.piece.Keima;
import com.shogi.domain.entity.piece.Kinsho;
import com.shogi.domain.entity.piece.Kyosha;
import com.shogi.domain.entity.piece.Ousho;
import com.shogi.domain.entity.piece.Piece;
import com.shogi.domain.valueobject.PieceType;
import com.shogi.domain.valueobject.Player;

public class PieceFactory {
  public static Piece createPiece(PieceType type, Player owner) {
    switch (type) {
      case FU:
        return new Fu(owner);
      case KY:
        return new Kyosha(owner);
      case KE:
        return new Keima(owner);
      case GI:
        return new Ginsho(owner);
      case KI:
        return new Kinsho(owner);
      case KA:
        return new Kaku(owner);
      case HI:
        return new Hisha(owner);
      case OU:
        return new Ousho(owner);
      default:
        throw new IllegalArgumentException(type + "は無効な駒の種類です");
    }
  }
}
