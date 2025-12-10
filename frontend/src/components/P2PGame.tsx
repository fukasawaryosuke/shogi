import { useEffect, useState, useRef } from "react";
import { usePeer } from "../hooks/usePeer";
import Peer from "./connection/Peer";
import Board from "./Board";
import Stand from "./Stand";
import Turn from "./Turn";
import { Wasm } from "../utils/wasm";
import type { DataConnection } from "peerjs";

type GameAction =
  | { type: "move"; fromX: number; fromY: number; toX: number; toY: number }
  | { type: "drop"; pieceName: string; x: number; y: number }
  | { type: "promote"; x: number; y: number }
  | { type: "no-promote" }
  | { type: "sync"; board: any; stand: any; turn: string }
  | { type: "restart" };

export default function P2PGame() {
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
  const [myPlayer, setMyPlayer] = useState<"先手" | "後手" | null>(null);
  const [opponentConnected, setOpponentConnected] = useState(false);
  const [showPlayerInfo, setShowPlayerInfo] = useState(false);
  const [isInCheck, setIsInCheck] = useState(false);
  const [checkMessage, setCheckMessage] = useState<string | null>(null);

  const { peer, peerId, isConnected, connectToPeer } = usePeer();
  const connectionRef = useRef<DataConnection | null>(null);

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

  // PeerJS接続管理
  useEffect(() => {
    if (!peer) return;

    // 接続を受け入れる側(後手)
    peer.on("connection", (conn) => {
      connectionRef.current = conn;
      setOpponentConnected(true);
      setMyPlayer("後手");
      setShowPlayerInfo(true);
      setTimeout(() => setShowPlayerInfo(false), 3000);

      conn.on("data", (data: GameAction) => {
        handleReceivedAction(data);
      });

      conn.on("close", () => {
        setOpponentConnected(false);
        connectionRef.current = null;
      });

      // 現在の状態を送信
      if (wasm) {
        conn.send({
          type: "sync",
          board: wasm.getBoard(),
          stand: wasm.getStand(),
          turn: wasm.getTurn(),
        } as GameAction);
      }
    });
  }, [peer, wasm]);

  const handleConnectToPeer = (opponentId: string) => {
    if (!peer) return;

    connectToPeer(opponentId);
    const conn = peer.connect(opponentId);

    conn.on("open", () => {
      connectionRef.current = conn;
      setOpponentConnected(true);
      setMyPlayer("先手");
      setShowPlayerInfo(true);
      setTimeout(() => setShowPlayerInfo(false), 3000);
    });

    conn.on("data", (data: GameAction) => {
      handleReceivedAction(data);
    });

    conn.on("close", () => {
      setOpponentConnected(false);
      connectionRef.current = null;
    });
  };

  const sendAction = (action: GameAction) => {
    if (connectionRef.current && connectionRef.current.open) {
      connectionRef.current.send(action);
    }
  };

  const handleReceivedAction = (action: GameAction) => {
    if (!wasm) return;

    switch (action.type) {
      case "move":
        const moveError = wasm.move(
          action.fromX,
          action.fromY,
          action.toX,
          action.toY
        );
        if (!moveError) {
          const mustPromote = wasm.mustPromote(action.toX, action.toY);
          if (mustPromote) {
            wasm.promote(action.toX, action.toY);
            wasm.nextTurn();
            updateGameState();
          } else if (wasm.canChoosePromote(action.toX, action.toY)) {
            // 相手の成り判定待ち
            updateGameState();
          } else {
            wasm.nextTurn();
            updateGameState();
          }
        }
        break;

      case "drop":
        const dropError = wasm.drop(action.pieceName, action.x, action.y);
        if (!dropError) {
          wasm.nextTurn();
          updateGameState();
        }
        break;

      case "promote":
        wasm.promote(action.x, action.y);
        wasm.nextTurn();
        updateGameState();
        break;

      case "no-promote":
        wasm.nextTurn();
        updateGameState();
        break;

      case "sync":
        setBoard(action.board);
        setStand(action.stand);
        setTurn(action.turn);
        break;

      case "restart":
        handleRestart();
        break;
    }
  };

  const updateGameState = () => {
    setTurn(wasm.getTurn());
    setBoard(wasm.getBoard());
    setStand(wasm.getStand());
    checkGameOver();
  };

  const handleCellClick = (x: number, y: number) => {
    if (gameOver || !myPlayer || turn !== myPlayer || !opponentConnected)
      return;

    if (selectedPiece) {
      // 持ち駒を打つ
      const error = wasm.drop(selectedPiece, x, y);

      if (error) {
        setMoveError(error);
        setSelectedPiece(null);
      } else {
        sendAction({
          type: "drop",
          pieceName: selectedPiece,
          x,
          y,
        });
        wasm.nextTurn();
        updateGameState();
        setSelectedPiece(null);
        setMoveError(null);
      }
    } else if (!selectedPosition) {
      // 最初のクリック: 駒を選択
      // その位置に自分の駒が存在するかチェック
      const cell = board?.[y - 1]?.[x - 1];
      if (cell && cell.piece && cell.piece.owner === myPlayer) {
        setSelectedPosition({ x, y });
        setMoveError(null);
      }
    } else {
      // 2回目のクリック: 駒を移動
      const error = wasm.move(selectedPosition.x, selectedPosition.y, x, y);

      if (error) {
        setMoveError(error);
        setSelectedPosition(null);
      } else {
        const mustPromote = wasm.mustPromote(x, y);
        const canChoose = wasm.canChoosePromote(x, y);

        if (mustPromote) {
          wasm.promote(x, y);
          sendAction({
            type: "move",
            fromX: selectedPosition.x,
            fromY: selectedPosition.y,
            toX: x,
            toY: y,
          });
          sendAction({ type: "promote", x, y });
          wasm.nextTurn();
          updateGameState();
          setSelectedPosition(null);
          setMoveError(null);
        } else if (canChoose) {
          sendAction({
            type: "move",
            fromX: selectedPosition.x,
            fromY: selectedPosition.y,
            toX: x,
            toY: y,
          });
          setPromoteDialog({ x, y });
        } else {
          sendAction({
            type: "move",
            fromX: selectedPosition.x,
            fromY: selectedPosition.y,
            toX: x,
            toY: y,
          });
          wasm.nextTurn();
          updateGameState();
          setSelectedPosition(null);
          setMoveError(null);
        }
      }
    }
  };

  const checkGameOver = () => {
    // 詰みチェック
    if (wasm.isCheckmate()) {
      const currentPlayer = wasm.getTurn();
      const winner = currentPlayer === "先手" ? "後手" : "先手";
      setGameOver(true);
      setWinner(winner);
      setIsInCheck(false);
      setCheckMessage(null);
      return true;
    }

    // 王手チェック
    if (wasm.isInCheck()) {
      setIsInCheck(true);
      setCheckMessage("王手");
      setTimeout(() => setCheckMessage(null), 3000);
    } else {
      setIsInCheck(false);
      setCheckMessage(null);
    }

    // 王将が取られたかチェック（念のため）
    if (wasm.isGameOver()) {
      const currentPlayer = wasm.getTurn();
      const winner = currentPlayer === "先手" ? "後手" : "先手";
      setGameOver(true);
      setWinner(winner);
      return true;
    }
    return false;
  };

  const handlePieceClick = (pieceName: string) => {
    if (gameOver || !myPlayer || turn !== myPlayer || !opponentConnected)
      return;

    setSelectedPiece(pieceName);
    setSelectedPosition(null);
    setMoveError(null);
  };

  const handlePromoteChoice = (shouldPromote: boolean) => {
    if (!promoteDialog) return;

    const { x, y } = promoteDialog;

    if (shouldPromote) {
      wasm.promote(x, y);
      sendAction({ type: "promote", x, y });
    } else {
      sendAction({ type: "no-promote" });
    }

    wasm.nextTurn();
    updateGameState();
    setSelectedPosition(null);
    setPromoteDialog(null);
    setMoveError(null);
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
    setIsInCheck(false);
    setCheckMessage(null);

    if (connectionRef.current && connectionRef.current.open) {
      sendAction({ type: "restart" });
    }
  };

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;
  if (!wasm) return <div>No data</div>;

  // 対戦相手が接続されていない場合は接続UIのみ表示
  if (!opponentConnected) {
    return (
      <div className="game-container">
        <h1>P2P対戦</h1>
        <Peer
          isConnected={isConnected}
          peerId={peerId}
          connectToPeer={handleConnectToPeer}
        />
        {isConnected && (
          <div className="waiting-message">対戦相手の接続を待っています...</div>
        )}
      </div>
    );
  }

  // 対戦相手が接続されたらゲーム画面を表示
  return (
    <div className="game-container">
      <h1>P2P対戦</h1>

      {showPlayerInfo && myPlayer && (
        <div className="player-info-toast">あなたは{myPlayer}です</div>
      )}

      <Turn turn={turn} />
      {checkMessage && <div className="check-message">{checkMessage}</div>}
      {moveError && <div className="error-message">{moveError}</div>}
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
      <div className="game-layout">
        {/* 後手視点では相手(先手)のStandを上に表示 */}
        <Stand
          player={myPlayer === "後手" ? "先手" : "後手"}
          pieces={stand?.[myPlayer === "後手" ? "先手" : "後手"] || []}
          onPieceClick={
            !gameOver &&
            turn === (myPlayer === "後手" ? "先手" : "後手") &&
            myPlayer === (myPlayer === "後手" ? "先手" : "後手") &&
            opponentConnected
              ? handlePieceClick
              : undefined
          }
          selectedPiece={selectedPiece}
          viewPlayer={myPlayer}
        />
        <Board
          board={board}
          selectedPosition={selectedPosition}
          onCellClick={
            !gameOver && opponentConnected ? handleCellClick : undefined
          }
          isInCheck={isInCheck}
          currentPlayer={turn}
          viewPlayer={myPlayer}
        />
        {/* 後手視点では自分(後手)のStandを下に表示 */}
        <Stand
          player={myPlayer === "後手" ? "後手" : "先手"}
          pieces={stand?.[myPlayer === "後手" ? "後手" : "先手"] || []}
          onPieceClick={
            !gameOver &&
            turn === (myPlayer === "後手" ? "後手" : "先手") &&
            myPlayer === (myPlayer === "後手" ? "後手" : "先手") &&
            opponentConnected
              ? handlePieceClick
              : undefined
          }
          selectedPiece={selectedPiece}
          viewPlayer={myPlayer}
        />
      </div>
    </div>
  );
}
