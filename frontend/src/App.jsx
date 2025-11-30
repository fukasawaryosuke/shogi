import { useEffect, useState } from "react";
import { usePeer } from "./hooks/usePeer";
import Peer from "./components/connection/peer";
import Board from "./components/Board";
import Stand from "./components/Stand";
import Turn from "./components/Turn";
import { Wasm } from "./utils/wasm";
import "./styles/App.css";

export default function App() {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [moveError, setMoveError] = useState(null);
  const [wasm, setWasm] = useState(null);
  const [turn, setTurn] = useState("");
  const [board, setBoard] = useState(null);
  const [stand, setStand] = useState(null);
  const [selectedPosition, setSelectedPosition] = useState(null);
  const [selectedPiece, setSelectedPiece] = useState(null);
  const [promoteDialog, setPromoteDialog] = useState(null);

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
    if (selectedPiece) {
      // 持ち駒を打つ
      const error = wasm.drop(selectedPiece, x, y);

      if (error) {
        setMoveError(error);
        setSelectedPiece(null);
      } else {
        // 成功: ターンを進めて盤面を更新
        wasm.nextTurn();
        setTurn(wasm.getTurn());
        setBoard(wasm.getBoard());
        setStand(wasm.getStand());
        setSelectedPiece(null);
        setMoveError(null);
      }
    } else if (!selectedPosition) {
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
        // 成功: 成り判定をチェック
        const mustPromote = wasm.mustPromote(x, y);
        const canChoose = wasm.canChoosePromote(x, y);

        if (mustPromote) {
          // 必須成り: 自動的に成る
          wasm.promote(x, y);
          wasm.nextTurn();
          setTurn(wasm.getTurn());
          setBoard(wasm.getBoard());
          setStand(wasm.getStand());
          setSelectedPosition(null);
          setMoveError(null);
        } else if (canChoose) {
          // 任意成り: ダイアログを表示
          setPromoteDialog({ x, y });
        } else {
          // 成れない: ターンを進める
          wasm.nextTurn();
          setTurn(wasm.getTurn());
          setBoard(wasm.getBoard());
          setStand(wasm.getStand());
          setSelectedPosition(null);
          setMoveError(null);
        }
      }
    }
  };

  const handlePieceClick = (pieceName) => {
    setSelectedPiece(pieceName);
    setSelectedPosition(null);
    setMoveError(null);
  };

  const handlePromoteChoice = (shouldPromote) => {
    if (!promoteDialog) return;

    const { x, y } = promoteDialog;

    if (shouldPromote) {
      wasm.promote(x, y);
    }

    // ターンを進めて盤面を更新
    wasm.nextTurn();
    setTurn(wasm.getTurn());
    setBoard(wasm.getBoard());
    setStand(wasm.getStand());
    setSelectedPosition(null);
    setPromoteDialog(null);
    setMoveError(null);
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
      {moveError && <div className="error-message">{moveError}</div>}
      {selectedPiece && (
        <div className="selected-piece-message">
          選択中の持ち駒: {selectedPiece}
        </div>
      )}
      {promoteDialog && (
        <div className="promote-dialog-overlay">
          <div className="promote-dialog-content">
            <h2 className="promote-dialog-title">駒を成りますか?</h2>
            <div className="promote-dialog-buttons">
              <button
                onClick={() => handlePromoteChoice(true)}
                className="promote-button"
              >
                成る
              </button>
              <button
                onClick={() => handlePromoteChoice(false)}
                className="no-promote-button"
              >
                成らない
              </button>
            </div>
          </div>
        </div>
      )}
      <Stand
        player="後手"
        pieces={stand?.["後手"] || []}
        onPieceClick={turn === "後手" ? handlePieceClick : undefined}
        selectedPiece={selectedPiece}
      />
      <Board
        board={board}
        selectedPosition={selectedPosition}
        onCellClick={handleCellClick}
      />
      <Stand
        player="先手"
        pieces={stand?.["先手"] || []}
        onPieceClick={turn === "先手" ? handlePieceClick : undefined}
        selectedPiece={selectedPiece}
      />
    </div>
  );
}
