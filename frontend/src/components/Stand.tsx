import type { Player } from "../types/schemas/player.zod";
import type { StandPiece } from "../types/schemas/stand.zod";
import { PieceAssetResolver } from "../utils/PieceAssetResolver";
import "../styles/Stand.css";

type StandProps = {
  player: Player;
  pieces: StandPiece[];
  onPieceClick?: (pieceType: string) => void;
  selectedPiece?: string | null;
};

export default function Stand({
  player,
  pieces,
  onPieceClick,
  selectedPiece,
}: StandProps) {
  const renderPiece = (name: string) => {
    const src = PieceAssetResolver.getImageUrl(name);
    if (!src) return null;
    const className =
      player === "後手" ? "stand-piece-image piece-gote" : "stand-piece-image";
    return <img src={src} alt={name} className={className} />;
  };

  return (
    <section
      className={`centered-section stand-section ${
        player === "後手" ? "stand-top" : "stand-bottom"
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
                player === "後手" ? "top-player" : ""
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
