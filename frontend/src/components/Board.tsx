import type { Board } from "../types/schemas/board.zod";
import "../styles/Board.css";

type BoardProps = {
  board: Board;
};

export default function Board({ board }: BoardProps) {
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

  const pieceFileMap: Record<string, string> = {
    歩: "fu_hyou.svg",
    香: "kyo_sha.svg",
    桂: "kei_ma.svg",
    銀: "gin_sho.svg",
    金: "kin_sho.svg",
    角: "kaku_gyou.svg",
    飛: "hi_sha.svg",
    王: "ou_sho.svg",
    玉: "gyoku_sho.svg",
    と: "to.svg",
    成香: "nari_kyo.svg",
    成桂: "nari_kei.svg",
    成銀: "nari_gin.svg",
    馬: "ryu_ma.svg",
    竜: "ryu_ou.svg",
  };

  const ownerDirMap: Record<string, string> = {
    先手: "sente",
    後手: "gote",
  };

  const getPieceSrc = (piece: string, owner: string) => {
    const dir = ownerDirMap[owner];
    const file = pieceFileMap[piece];
    if (!file) return null;
    return new URL(`../assets/${dir}/${file}`, import.meta.url).href;
  };

  const renderPiece = (name: string, owner: string) => {
    const src = getPieceSrc(name, owner);
    if (!src) return null;
    return <img src={src} alt={name} className="shogi-piece" />;
  };

  return (
    <section>
      <h2 className="board-title">Board</h2>
      <div className="board-wrapper">
        <div className="shogi-board-container">
          <table className="shogi-board" cellPadding={0} cellSpacing={0}>
            <tbody>
              {board.map((row, yIdx) => (
                <tr key={yIdx}>
                  {row.map((cell, xIdx) => (
                    <td key={xIdx} className="cell">
                      {cell && cell.piece
                        ? renderPiece(cell.piece.name, cell.piece.owner)
                        : null}
                    </td>
                  ))}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </section>
  );
}
