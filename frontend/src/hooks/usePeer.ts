import { useEffect, useRef, useState } from "react";
import Peer from "peerjs";
import type { ImportMetaEnv } from "vite/client";

type UsePeer = {
  peer: Peer | undefined;
  peerId: string;
  isConnected: boolean;
  connectToPeer: (opponentId: string) => void;
};

export function usePeer(): UsePeer {
  const [peer, setPeer] = useState<Peer | undefined>(undefined);
  const [peerId, setPeerId] = useState("");
  const [isConnected, setIsConnected] = useState(false);

  const resetState = () => {
    setPeerId("");
    setPeer(undefined);
    setIsConnected(false);
  };

  useEffect(() => {
    const env = import.meta.env as ImportMetaEnv;
    // Peerオブジェクトを作成
    const peer = new Peer({
      host: env.VITE_PEER_HOST || "localhost",
      port: env.VITE_PEER_PORT ? Number(env.VITE_PEER_PORT) : 9000,
      path: env.VITE_PEER_PATH || "/shogi",
      secure: env.VITE_PEER_SECURE === "true" || false,
    });

    setPeer(peer);

    // 接続成功時のイベントリスナー
    peer.on("open", (id) => {
      setPeerId(id);
      setIsConnected(true);
      console.log("PeerJS connected with ID:", id);
    });

    // 他のピアからの接続要求を受け入れるイベントリスナー
    peer.on("connection", (conn) => {
      console.log("PeerJS connection established with:", conn.peer);
      conn.on("data", (data) => {
        // テスト
        console.log("Received:", data);
      });
    });

    // 接続失敗時のイベントリスナー
    peer.on("error", (err) => {
      resetState();
      console.error("PeerJS error:", err);
    });

    // 接続切断時のイベントリスナー
    peer.on("disconnected", () => {
      resetState();
      console.log("PeerJS disconnected");
    });

    return () => {
      resetState();
      peer.destroy();
    };
  }, []); // コンポーネント初期化時に1回実行

  const connectToPeer = (opponentId: string) => {
    if (!peer) {
      console.error("Peer is not initialized");
      return;
    }

    if (opponentId === peerId) {
      console.error("Cannot connect to yourself");
      return;
    }

    const conn = peer.connect(opponentId);

    conn.on("open", () => {
      console.log("Connected to opponent:", opponentId);
      //テスト
      conn.send({ type: "greeting", message: "Hello!" });
    });
  };

  return { peer, peerId, isConnected, connectToPeer };
}
