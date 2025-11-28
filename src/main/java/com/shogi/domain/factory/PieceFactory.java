package com.shogi.domain.factory;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.piece.FuHyo;
import com.shogi.domain.valueobject.piece.GinSho;
import com.shogi.domain.valueobject.piece.HiSha;
import com.shogi.domain.valueobject.piece.KakuGyou;
import com.shogi.domain.valueobject.piece.KeiMa;
import com.shogi.domain.valueobject.piece.KinSho;
import com.shogi.domain.valueobject.piece.KyoSha;
import com.shogi.domain.valueobject.piece.OuSho;
import com.shogi.domain.valueobject.piece.Piece;
import com.shogi.domain.valueobject.piece.PieceType;

public class PieceFactory {
  public static Piece createPiece(PieceType type, Player owner) {
    if (type == null)
      throw new IllegalArgumentException("type is required");
    if (owner == null)
      throw new IllegalArgumentException("owner is required");

    switch (type) {
      case FU:
        return new FuHyo(owner);
      case KY:
        return new KyoSha(owner);
      case KE:
        return new KeiMa(owner);
      case GI:
        return new GinSho(owner);
      case KI:
        return new KinSho(owner);
      case KA:
        return new KakuGyou(owner);
      case HI:
        return new HiSha(owner);
      case OU:
        return new OuSho(owner);
      default:
        throw new IllegalArgumentException(type + " is invalid PieceType");
    }
  }
}
