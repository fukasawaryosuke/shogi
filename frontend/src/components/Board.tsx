import "../styles/Board.css";

export default function Board({ board }: Record<string, string>) {
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

  return (
    <section>
      <h2 className="board-title">Board</h2>
      <div className="board-wrapper">
        <div className="shogi-board-container">
          <table className="shogi-board" cellPadding={0} cellSpacing={0}>
            <tbody>
              <tr>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
              </tr>
              <tr>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
              </tr>
              <tr>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
              </tr>
              <tr>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
              </tr>
              <tr>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
              </tr>
              <tr>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
              </tr>
              <tr>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
              </tr>
              <tr>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
              </tr>
              <tr>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
                <td className="cell"></td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </section>
  );
}
