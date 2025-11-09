import React from "react";

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

  // スタイル（簡易）
  const containerStyle: React.CSSProperties = {
    display: "inline-grid",
    gridTemplateColumns: `40px repeat(${size}, 48px)`,
    gap: 4,
    alignItems: "center",
    fontFamily:
      "system-ui, -apple-system, 'Segoe UI', Roboto, 'Helvetica Neue', Arial",
  };

  const labelStyle: React.CSSProperties = {
    height: 28,
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    fontSize: 12,
    color: "#333",
  };

  const cellStyle: React.CSSProperties = {
    width: 48,
    height: 48,
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    border: "1px solid #ccc",
    background: "#fffef6",
    fontSize: 14,
    fontWeight: 600,
    boxSizing: "border-box",
  };

  const pieceStyle = (piece: string): React.CSSProperties => {
    const lower = (piece || "").toLowerCase();
    const base: React.CSSProperties = { ...cellStyle };
    if (
      lower.includes("sente") ||
      lower.includes("先") ||
      lower.startsWith("▲")
    ) {
      base.color = "#000";
      base.background = "#fff";
    } else if (
      lower.includes("gote") ||
      lower.includes("後") ||
      lower.startsWith("△")
    ) {
      base.color = "#900";
      base.background = "#fff";
    }
    return base;
  };

  return (
    <section>
      <h2>Board</h2>
      <div style={containerStyle}>
        {/* 左上の空セル */}
        <div style={{ width: 40 }} />

        {/* 列ラベル */}
        {Array.from({ length: size }, (_, i) => (
          <div key={`col-${i}`} style={labelStyle}>
            {i + 1}
          </div>
        ))}

        {/* 各行（行ラベル + 9セル） */}
        {cells.map((rowCells, r) => (
          <React.Fragment key={`row-${r}`}>
            <div style={labelStyle}>{rankChars[r].toUpperCase()}</div>
            {rowCells.map((p, c) => (
              <div
                key={`cell-${r}-${c}`}
                style={p ? pieceStyle(p) : cellStyle}
                title={p || `${c + 1}${rankChars[r]}`}
              >
                {p ? shortenPieceLabel(p) : ""}
              </div>
            ))}
          </React.Fragment>
        ))}
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
