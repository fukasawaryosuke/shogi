import { useEffect, useState } from "react";
import { usePeer } from "./hooks/usePeer";
import Peer from "./components/connection/peer";
import Board from "./components/Board";
import Stand from "./components/Stand";
import Turn from "./components/Turn";
import { WasmGameLoader } from "./utils/wasmLoader";

export default function App() {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [wasmLoader, setWasmLoader] = useState(null);

  const { peer, peerId, isConnected, connectToPeer } = usePeer();

  useEffect(() => {
    (async () => {
      try {
        const loader = await WasmGameLoader.init();
        setWasmLoader(loader);
        loader.main();
        const board = loader.getBoard(game);
        const stand = loader.getStand(game);
        const turn = loader.getTurn(game);
        console.log("WASM Board:", board);
        console.log("WASM Stand:", stand);
        console.log("WASM Turn:", turn);
      } catch (e) {
        setError(e.stack || e.message || JSON.stringify(e));
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;
  if (!state) return <div>No data</div>;

  const { board, stand, turn } = state;

  return (
    <div className="app-container">
      <h1>P2P Shogi</h1>
      <Peer
        isConnected={isConnected}
        peerId={peerId}
        connectToPeer={connectToPeer}
      />
      <Turn turn={turn} />
      <Stand stand={stand} />
      <Board board={board} />
      <Stand stand={stand} />
    </div>
  );
}
