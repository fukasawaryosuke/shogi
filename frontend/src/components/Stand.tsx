import type { Player } from "../types/schemas/player.zod";
import type { StandPiece } from "../types/schemas/stand.zod";
import { PieceAssetResolver } from "../utils/PieceAssetResolver";
import "../styles/Stand.css";

type StandProps = {
  player: Player;
  pieces: StandPiece[];
  onPieceClick?: (pieceType: string) => void;
  selectedPiece?: string | null;
  viewPlayer?: "先手" | "後手" | null;
};

export default function Stand({
  player,
  pieces,
  onPieceClick,
  selectedPiece,
  viewPlayer = null,
}: StandProps) {
  const renderPiece = (name: string) => {
    const src = PieceAssetResolver.getImageUrl(name);
    if (!src) return null;
    // 自分から見て相手の持ち駒を回転させる
    const shouldRotate = viewPlayer ? player !== viewPlayer : player === "後手";
    const className = shouldRotate
      ? "stand-piece-image piece-rotated"
      : "stand-piece-image";
    return <img src={src} alt={name} className={className} />;
  };

  // 自分のStandは下、相手のStandは上に表示
  const isMyStand = viewPlayer ? player === viewPlayer : player === "先手";
  const isTopPosition = !isMyStand;

  return (
    <section
      className={`centered-section stand-section ${
        isTopPosition ? "stand-top" : "stand-bottom"
      }`}
    >
      <ul className="stand-pieces-list">
        {pieces &&
          pieces.length > 0 &&
          pieces.map((piece) => (
            <li
              key={piece.type}
              onClick={() => onPieceClick?.(piece.type)}
              className={`stand-piece-item ${
                selectedPiece === piece.type ? "selected" : ""
              } ${!onPieceClick ? "disabled" : ""} ${
                isTopPosition ? "top-player" : ""
              }`}
            >
              {renderPiece(piece.name)}
              <span className="stand-piece-count">×{piece.count}</span>
            </li>
          ))}
      </ul>
    </section>
  );
}
