import { useState } from "react";
import ModeSelector from "./components/ModeSelector";
import LocalGame from "./components/LocalGame";
import P2PGame from "./components/P2PGame";
import "./styles/App.css";

export default function App() {
  const [mode, setMode] = useState(null);

  if (!mode) {
    return <ModeSelector onSelectMode={setMode} />;
  }

  return (
    <div className="app-container">
      <button className="back-button" onClick={() => setMode(null)}>
        ← モード選択に戻る
      </button>
      {mode === "local" ? <LocalGame /> : <P2PGame />}
    </div>
  );
}
