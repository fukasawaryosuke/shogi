import type { StandPiece } from "../types/schemas/stand.zod";
import "../styles/Stand.css";

type StandProps = {
  stand: StandPiece[];
  onPieceClick?: (pieceType: string) => void;
  selectedPiece?: string | null;
};

export default function Stand({
  stand,
  onPieceClick,
  selectedPiece,
}: StandProps) {
  if (!stand || stand.length === 0) {
    return (
      <section className="centered-section">
        <h2>持ち駒</h2>
        <p>なし</p>
      </section>
    );
  }

  return (
    <section className="centered-section">
      <h2>持ち駒</h2>
      <ul className="stand-pieces-list">
        {stand.map((piece) => (
          <li
            key={piece.type}
            onClick={() => onPieceClick?.(piece.type)}
            className={`stand-piece-item ${
              selectedPiece === piece.type ? "selected" : ""
            } ${!onPieceClick ? "disabled" : ""}`}
          >
            {piece.name}: {piece.count}
          </li>
        ))}
      </ul>
    </section>
  );
}
