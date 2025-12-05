import { useEffect, useState } from "react";
import Board from "./Board";
import Stand from "./Stand";
import Turn from "./Turn";
import { Wasm } from "../utils/wasm";

export default function LocalGame() {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [moveError, setMoveError] = useState<string | null>(null);
  const [wasm, setWasm] = useState<Wasm | null>(null);
  const [turn, setTurn] = useState("");
  const [board, setBoard] = useState<any>(null);
  const [stand, setStand] = useState<any>(null);
  const [selectedPosition, setSelectedPosition] = useState<{
    x: number;
    y: number;
  } | null>(null);
  const [selectedPiece, setSelectedPiece] = useState<string | null>(null);
  const [promoteDialog, setPromoteDialog] = useState<{
    x: number;
    y: number;
  } | null>(null);
  const [gameOver, setGameOver] = useState(false);
  const [winner, setWinner] = useState<string | null>(null);

  useEffect(() => {
    (async () => {
      try {
        const wasm = await Wasm.init();
        wasm.main();
        setWasm(wasm);
        setTurn(wasm.getTurn());
        setBoard(wasm.getBoard());
        setStand(wasm.getStand());
      } catch (e: any) {
        setError(e.stack || e.message || JSON.stringify(e));
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  const handleCellClick = (x: number, y: number) => {
    if (gameOver) return;

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
        checkGameOver();
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
          checkGameOver();
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
          checkGameOver();
        }
      }
    }
  };

  const checkGameOver = () => {
    if (wasm.isGameOver()) {
      // 現在のターンのプレイヤーが負け(王将を取られた)
      const currentPlayer = wasm.getTurn();
      const winner = currentPlayer === "先手" ? "後手" : "先手";
      setGameOver(true);
      setWinner(winner);
      return true;
    }
    return false;
  };

  const handlePieceClick = (pieceName: string) => {
    if (gameOver) return;

    setSelectedPiece(pieceName);
    setSelectedPosition(null);
    setMoveError(null);
  };

  const handlePromoteChoice = (shouldPromote: boolean) => {
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
    checkGameOver();
  };

  const handleRestart = () => {
    wasm.main();
    setTurn(wasm.getTurn());
    setBoard(wasm.getBoard());
    setStand(wasm.getStand());
    setGameOver(false);
    setWinner(null);
    setSelectedPosition(null);
    setSelectedPiece(null);
    setMoveError(null);
    setPromoteDialog(null);
  };

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;
  if (!wasm) return <div>No data</div>;

  return (
    <div className="game-container">
      <h1>ローカル対戦</h1>
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
      {gameOver && (
        <div className="promote-dialog-overlay">
          <div className="promote-dialog-content">
            <h2 className="promote-dialog-title">ゲーム終了</h2>
            <p className="game-over-message">{winner}の勝ちです!</p>
            <button
              onClick={handleRestart}
              className="promote-button replay-button"
            >
              もう一度プレイ
            </button>
          </div>
        </div>
      )}
      <Stand
        player="後手"
        pieces={stand?.["後手"] || []}
        onPieceClick={
          !gameOver && turn === "後手" ? handlePieceClick : undefined
        }
        selectedPiece={selectedPiece}
      />
      <Board
        board={board}
        selectedPosition={selectedPosition}
        onCellClick={!gameOver ? handleCellClick : undefined}
      />
      <Stand
        player="先手"
        pieces={stand?.["先手"] || []}
        onPieceClick={
          !gameOver && turn === "先手" ? handlePieceClick : undefined
        }
        selectedPiece={selectedPiece}
      />
    </div>
  );
}
