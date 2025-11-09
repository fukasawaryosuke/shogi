import "../styles/Board.css";

type Props = { board?: Record<string, string> };

export default function Board({ board }: Props) {
  //    "board": {
  //         "2, 9": "桂",
  //         "2, 8": "角",
  //         "2, 7": "歩",
  //         "4, 9": "金",
  //         "6, 3": "歩",
  //         "8, 7": "歩",
  //         "4, 1": "金",
  //         "4, 3": "歩",
  //         "6, 7": "歩",
  //         "8, 8": "飛",
  //         "6, 9": "金",
  //         "8, 9": "桂",
  //         "4, 7": "歩",
  //         "2, 3": "歩",
  //         "2, 2": "飛",
  //         "2, 1": "桂",
  //         "8, 2": "角",
  //         "8, 3": "歩",
  //         "6, 1": "金",
  //         "8, 1": "桂",
  //         "1, 9": "香",
  //         "1, 7": "歩",
  //         "3, 9": "銀",
  //         "3, 7": "歩",
  //         "5, 9": "王",
  //         "5, 1": "玉",
  //         "9, 7": "歩",
  //         "7, 3": "歩",
  //         "5, 3": "歩",
  //         "7, 7": "歩",
  //         "9, 9": "香",
  //         "5, 7": "歩",
  //         "7, 9": "銀",
  //         "1, 3": "歩",
  //         "1, 1": "香",
  //         "3, 3": "歩",
  //         "9, 3": "歩",
  //         "3, 1": "銀",
  //         "9, 1": "香",
  //         "7, 1": "銀"
  //     }
  // }

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

  const LENGTH = 9;
  const ROWS = Array.from({ length: LENGTH });
  const COLS = Array.from({ length: LENGTH });

  const pieceFileMap: Record<string, string> = {
    歩: "sente/fu_hyou.svg",
    香: "sente/kyo_sha.svg",
    桂: "sente/kei_ma.svg",
    銀: "sente/gin_sho.svg",
    金: "sente/kin_sho.svg",
    角: "sente/kaku_gyou.svg",
    飛: "sente/hi_sha.svg",
    王: "sente/ou_sho.svg",
    玉: "sente/gyoku_sho.svg",
    と: "sente/to.svg",
    成香: "sente/nari_kyo.svg",
    成桂: "sente/nari_kei.svg",
    成銀: "sente/nari_gin.svg",
    馬: "sente/ryu_ma.svg",
    竜: "sente/ryu_ou.svg",
  };

  const getPieceSrc = (piece: string) => {
    const file = pieceFileMap[piece];
    if (!file) return null;
    return new URL(`../assets/${file}`, import.meta.url).href;
  };

  const renderPiece = (piece: string) => {
    const src = getPieceSrc(piece);
    if (!src) return null;
    return <img src={src} alt={piece} className="shogi-piece" />;
  };

  return (
    <section>
      <h2 className="board-title">Board</h2>
      <div className="board-wrapper">
        <div className="shogi-board-container">
          <table className="shogi-board" cellPadding={0} cellSpacing={0}>
            <tbody>
              {ROWS.map((_, rowIndex) => {
                const row = rowIndex + 1; // 1..9
                return (
                  <tr key={row}>
                    {COLS.map((_, colIndex) => {
                      const col = colIndex + 1; // 1..9
                      // board のキーは "col, row" の形式 (例: "2, 9")
                      const key = `${col}, ${row}`;
                      const piece = board?.[key];
                      return (
                        <td key={col} className="cell">
                          {piece ? renderPiece(piece) : null}
                        </td>
                      );
                    })}
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      </div>
    </section>
  );
}
