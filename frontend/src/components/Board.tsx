import "./Board.css";

type BoardProps = {
  board: Record<string, string>;
};

export default function Board({ board }: BoardProps) {
  // 9x9 のセル初期化
  const size = 9;
  const emptyRow = Array(size).fill("");
  const cells: string[][] = Array.from({ length: size }, () => [...emptyRow]);

  // pos 例: "7g" -> file=7 (1-9), rank='g' (a-i)
  const rankChars = "abcdefghi";

  Object.entries(board || {}).forEach(([pos, piece]) => {
    const m = pos.match(/^(\d)([a-i])$/i);
    if (!m) return;
    const file = parseInt(m[1], 10); // 1..9
    const rank = m[2].toLowerCase();
    const col = file - 1;
    const row = rankChars.indexOf(rank); // 0..8

    if (row >= 0 && col >= 0 && row < size && col < size) {
      cells[row][col] = piece;
    }
  });

  const isSente = (raw: string) => {
    const s = (raw || "").toString();
    const lower = s.toLowerCase();
    return (
      lower.includes("sente") || lower.includes("先") || lower.startsWith("▲")
    );
  };
  const isGote = (raw: string) => {
    const s = (raw || "").toString();
    const lower = s.toLowerCase();
    return (
      lower.includes("gote") || lower.includes("後") || lower.startsWith("△")
    );
  };

  return (
    <section>
      <h2 className="board-title">Board</h2>
      <div className="board-wrapper">
        <div className="shogi-board-container">
          {cells.map((rowCells, r) =>
            rowCells.map((p, c) => {
              const classes = ["board-cell"];
              if (p) {
                if (isSente(p)) classes.push("sente");
                else if (isGote(p)) classes.push("gote");
              }
              return (
                <div
                  key={`cell-${r}-${c}`}
                  className={classes.join(" ")}
                  title={p || `${c + 1}${rankChars[r]}`}
                >
                  {p ? renderPieceSVG(p) : ""}
                </div>
              );
            })
          )}
        </div>
      </div>
    </section>
  );
}

// 駒表示を短く・見やすくするヘルパー（必要に応じて拡張）
function shortenPieceLabel(raw: string): string {
  if (!raw) return "";
  // 例: "FU" / "SENTE:FU" / "▲FU" などを想定して短縮
  const s = raw.replace(/\s+/g, "");
  // 優先的に英字2文字だけ表示
  const m = s.match(/[A-Z]{1,3}/i);
  if (m) return m[0].toUpperCase();
  // 代替に先頭3文字
  return s.slice(0, 3);
}

// --- SVG rendering helpers ---
function renderPieceSVG(raw: string) {
  const filename = pieceFilenameFor(raw);
  if (!filename) {
    // fallback to a short label if no mapping
    return shortenPieceLabel(raw);
  }
  // Vite-compatible dynamic URL to an asset in src
  const src = new URL(`../assets/${filename}`, import.meta.url).href;
  return <img src={src} alt={raw} className="piece-svg" />;
}

function pieceFilenameFor(raw: string): string | null {
  if (!raw) return null;
  const s = raw.toString();
  const upper = s.toUpperCase();

  // 1) If the raw contains Japanese kanji, prefer that detection (robust across data formats)
  if (s.includes("歩")) return "fu.svg";
  if (s.includes("香")) return "kyosha.svg";
  if (s.includes("桂")) return "keima.svg";
  if (s.includes("銀")) return "ginsho.svg";
  if (s.includes("金")) return "kinsho.svg";
  if (s.includes("角")) return "kakugyou.svg";
  if (s.includes("飛")) return "hisha.svg";
  if (s.includes("王")) return "ousho.svg";
  if (s.includes("玉")) return "gyokusho.svg";
  if (
    s.includes("龍") ||
    s.includes("竜") ||
    upper.includes("RYU") ||
    upper.includes("RY")
  )
    return "ryuu.svg";
  if (s.includes("馬") || upper.includes("UMA")) return "uma.svg";
  if (s.includes("と") || upper === "TO" || upper.includes("TO"))
    return "to.svg";

  // 2) Promoted two-char forms (成X)
  if (s.includes("成銀") || upper.includes("NARIGIN")) return "narigin.svg";
  if (
    s.includes("成桂") ||
    upper.includes("NARIKEI") ||
    upper.includes("NARIKY")
  )
    return "narikei.svg";
  if (
    s.includes("成香") ||
    upper.includes("NARIKYO") ||
    upper.includes("NARIKY")
  )
    return "narikyo.svg";

  // 3) English-ish tokens (common short codes). Try to extract a 1-4 letter code
  const m = upper.match(/[A-Z]{1,4}/);
  const code = m ? m[0] : null;
  switch (code) {
    case "FU":
    case "P":
      return "fu.svg";
    case "KY":
    case "L":
      return "kyosha.svg";
    case "KE":
    case "N":
      return "keima.svg";
    case "GI":
    case "S":
      return "ginsho.svg";
    case "KI":
    case "G":
      return "kinsho.svg";
    case "KA":
    case "B":
      return "kakugyou.svg";
    case "HI":
    case "R":
      return "hisha.svg";
    case "OU":
    case "K":
      return "ousho.svg";
    case "GY":
    case "GYOKU":
      return "gyokusho.svg";
    case "TO":
      return "to.svg";
    case "RY":
    case "RYU":
      return "ryuu.svg";
    case "UM":
    case "UMA":
      return "uma.svg";
    case "NG":
      return "narigin.svg";
    default:
      return null;
  }
}
