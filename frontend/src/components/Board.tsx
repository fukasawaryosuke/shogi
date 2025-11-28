import type { Board } from "../types/schemas/board.zod";
import { PieceAssetResolver } from "../utils/PieceAssetResolver";
import "../styles/Board.css";

type BoardProps = {
  board: Board;
  selectedPosition: { x: number; y: number } | null;
  onCellClick: (x: number, y: number) => void;
};

export default function Board({
  board,
  selectedPosition,
  onCellClick,
}: BoardProps) {
  // 駒の初期配置
  // GOTE側
  // ・ 1 2 3 4 5 6 7 8 9
  // 1 香 桂 銀 金 王 金 銀 桂 香
  // 2 ・ 飛 ・ ・ ・ ・ ・ 角 ・
  // 3 歩 歩 歩 歩 歩 歩 歩 歩 歩
  // 4 ・ ・ ・ ・ ・ ・ ・ ・ ・
  // 5 ・ ・ ・ ・ ・ ・ ・ ・ ・
  // 6 ・ ・ ・ ・ ・ ・ ・ ・ ・
  // 7 歩 歩 歩 歩 歩 歩 歩 歩 歩
  // 8 ・ 角 ・ ・ ・ ・ ・ 飛 ・
  // 9 香 桂 銀 金 王 金 銀 桂 香
  // SENTE側

  const renderPiece = (name: string, owner: string) => {
    const src = PieceAssetResolver.getImageUrl(name, owner);
    if (!src) return null;
    return <img src={src} alt={name} className="shogi-piece" />;
  };

  return (
    <section>
      <div className="board-wrapper">
        <div className="shogi-board-container">
          <table className="shogi-board" cellPadding={0} cellSpacing={0}>
            <tbody>
              {board.map((row, yIdx) => (
                <tr key={yIdx}>
                  {row.map((cell, xIdx) => {
                    const isSelected =
                      selectedPosition &&
                      selectedPosition.x === xIdx + 1 &&
                      selectedPosition.y === yIdx + 1;

                    return (
                      <td
                        key={xIdx}
                        className={`cell ${isSelected ? "selected" : ""}`}
                        onClick={() => onCellClick(xIdx + 1, yIdx + 1)}
                      >
                        {cell && cell.piece
                          ? renderPiece(cell.piece.name, cell.piece.owner)
                          : null}
                      </td>
                    );
                  })}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </section>
  );
}
