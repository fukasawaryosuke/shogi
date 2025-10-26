package com.shogi.domain.factory;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.PieceType;
import com.shogi.domain.valueobject.piece.FuHyo;
import com.shogi.domain.valueobject.piece.GinSho;
import com.shogi.domain.valueobject.piece.HiSha;
import com.shogi.domain.valueobject.piece.KakuGyou;
import com.shogi.domain.valueobject.piece.KeiMa;
import com.shogi.domain.valueobject.piece.KinSho;
import com.shogi.domain.valueobject.piece.KyoSha;
import com.shogi.domain.valueobject.piece.OuSho;
import com.shogi.domain.valueobject.piece.Piece;

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

  public static Piece clonePiece(Piece piece, Player owner) {
    if (piece == null)
      throw new IllegalArgumentException("piece is required");
    if (owner == null)
      throw new IllegalArgumentException("owner is required");

    if (piece instanceof FuHyo) {
      return new FuHyo(owner);
    } else if (piece instanceof KyoSha) {
      return new KyoSha(owner);
    } else if (piece instanceof KeiMa) {
      return new KeiMa(owner);
    } else if (piece instanceof GinSho) {
      return new GinSho(owner);
    } else if (piece instanceof KinSho) {
      return new KinSho(owner);
    } else if (piece instanceof KakuGyou) {
      return new KakuGyou(owner);
    } else if (piece instanceof HiSha) {
      return new HiSha(owner);
    } else if (piece instanceof OuSho) {
      return new OuSho(owner);
    } else {
      throw new IllegalArgumentException(piece.getClass() + " is invalid Piece");
    }
  }
}
