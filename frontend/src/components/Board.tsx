import type { Board } from "../types/schemas/board.zod";
import { PieceAssetResolver } from "../utils/PieceAssetResolver";
import "../styles/Board.css";

type BoardProps = {
  board: Board;
  selectedPosition: { x: number; y: number } | null;
  onCellClick: (x: number, y: number) => void;
  isInCheck?: boolean;
  currentPlayer?: string;
  viewPlayer?: "先手" | "後手" | null;
};

export default function Board({
  board,
  selectedPosition,
  onCellClick,
  isInCheck = false,
  currentPlayer = "",
  viewPlayer = null,
}: BoardProps) {
  // 駒の初期配置
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

  const renderPiece = (name: string, owner: string) => {
    const src = PieceAssetResolver.getImageUrl(name);
    if (!src) return null;
    // 後手視点では盤面データが反転されているため、相手の駒を回転
    const shouldRotate = viewPlayer ? owner !== viewPlayer : owner === "後手";
    const className = shouldRotate
      ? "shogi-piece piece-rotated"
      : "shogi-piece";
    return <img src={src} alt={name} className={className} />;
  };

  // 後手視点では盤面データを反転
  const displayBoard =
    viewPlayer === "後手"
      ? board.map((row) => [...row].reverse()).reverse()
      : board;

  return (
    <section>
      <div className="board-wrapper">
        <div className="shogi-board-container">
          <table className="shogi-board" cellPadding={0} cellSpacing={0}>
            <tbody>
              {displayBoard.map((row, yIdx) => (
                <tr key={yIdx}>
                  {row.map((cell, xIdx) => {
                    // 後手視点では表示インデックスをゲーム座標に変換
                    const gameX = viewPlayer === "後手" ? 9 - xIdx : xIdx + 1;
                    const gameY = viewPlayer === "後手" ? 9 - yIdx : yIdx + 1;

                    const isSelected =
                      selectedPosition &&
                      selectedPosition.x === gameX &&
                      selectedPosition.y === gameY;

                    // 王手されている王かチェック
                    const isKingInCheck =
                      isInCheck &&
                      cell &&
                      cell.piece &&
                      (cell.piece.name === "王" || cell.piece.name === "玉") &&
                      cell.piece.owner === currentPlayer;

                    return (
                      <td
                        key={xIdx}
                        className={`cell ${isSelected ? "selected" : ""} ${
                          isKingInCheck ? "in-check" : ""
                        }`}
                        onClick={() => onCellClick(gameX, gameY)}
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
