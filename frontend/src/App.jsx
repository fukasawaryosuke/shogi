import { useEffect, useState, useRef } from "react";
import { usePeer } from "./hooks/usePeer";
import Peer from "./components/connection/peer";
import Board from "./components/Board";
import Stand from "./components/Stand";
import Turn from "./components/Turn";
import { Wasm } from "./utils/wasm";

export default function App() {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [moveError, setMoveError] = useState(null);
  const [wasm, setWasm] = useState(null);
  const [turn, setTurn] = useState("");
  const [board, setBoard] = useState(null);
  const [stand, setStand] = useState(null);
  const [selectedPosition, setSelectedPosition] = useState(null);

  const { peer, peerId, isConnected, connectToPeer } = usePeer();

  useEffect(() => {
    (async () => {
      try {
        const wasm = await Wasm.init();
        wasm.main();
        setWasm(wasm);
        setTurn(wasm.getTurn());
        setBoard(wasm.getBoard());
        setStand(wasm.getStand());
      } catch (e) {
        setError(e.stack || e.message || JSON.stringify(e));
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  const handleCellClick = (x, y) => {
    if (!selectedPosition) {
      // 最初のクリック: 駒を選択
      setSelectedPosition({ x, y });
      setMoveError(null);
    } else {
      // 2回目のクリック: 駒を移動
      const error = wasm.move(selectedPosition.x, selectedPosition.y, x, y);

      if (error) {
        // エラー発生
        setMoveError(error);
        setSelectedPosition(null);
      } else {
        // 成功: ターンを進めて盤面を更新
        wasm.nextTurn();
        setTurn(wasm.getTurn());
        setBoard(wasm.getBoard());
        setStand(wasm.getStand());
        setSelectedPosition(null);
        setMoveError(null);
      }
    }
  };

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;
  if (!wasm) return <div>No data</div>;

  return (
    <div className="app-container">
      <h1>P2P Shogi</h1>
      <Peer
        isConnected={isConnected}
        peerId={peerId}
        connectToPeer={connectToPeer}
      />
      <Turn turn={turn} />
      {moveError && (
        <div style={{ color: "red", textAlign: "center", padding: "8px" }}>
          {moveError}
        </div>
      )}
      <Stand stand={stand["後手"]} />
      <Board
        board={board}
        selectedPosition={selectedPosition}
        onCellClick={handleCellClick}
      />
      <Stand stand={stand["先手"]} />
    </div>
  );
}
