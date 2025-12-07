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
    const src = PieceAssetResolver.getImageUrl(name, player);
    if (!src) return null;
    return <img src={src} alt={name} className="stand-piece-image" />;
  };

  return (
    <section className="centered-section stand-section">
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
