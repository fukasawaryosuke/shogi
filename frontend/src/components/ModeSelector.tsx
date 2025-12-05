export default function ModeSelector({
  onSelectMode,
}: {
  onSelectMode: (mode: "local" | "p2p") => void;
}) {
  return (
    <div className="mode-selector">
      <h1>Shogi</h1>
      <div className="mode-buttons">
        <button className="mode-button" onClick={() => onSelectMode("local")}>
          <p>ローカル対戦</p>
          <span className="mode-description">1人で対戦</span>
        </button>
        <button className="mode-button" onClick={() => onSelectMode("p2p")}>
          <p>P2P対戦</p>
          <span className="mode-description">オンラインで対戦</span>
        </button>
      </div>
    </div>
  );
}
