package com.shogi.domain.factory;

import com.shogi.domain.entity.piece.FuHyo;
import com.shogi.domain.entity.piece.GinSho;
import com.shogi.domain.entity.piece.HiSha;
import com.shogi.domain.entity.piece.KakuGyou;
import com.shogi.domain.entity.piece.KeiMa;
import com.shogi.domain.entity.piece.KinSho;
import com.shogi.domain.entity.piece.KyoSha;
import com.shogi.domain.entity.piece.OuSho;
import com.shogi.domain.entity.piece.Piece;
import com.shogi.domain.valueobject.PieceType;
import com.shogi.domain.valueobject.Player;

public class PieceFactory {
  public static Piece createPiece(PieceType type, Player owner) {
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
        throw new IllegalArgumentException(type + "は無効な駒の種類です");
    }
  }
}
