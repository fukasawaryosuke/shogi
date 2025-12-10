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
    });

    // 他のピアからの接続要求を受け入れるイベントリスナー
    peer.on("connection", (conn) => {
      conn.on("data", (data) => {
        // 接続確認用
      });
    });

    // 接続失敗時のイベントリスナー
    peer.on("error", (err) => {
      resetState();
    });

    // 接続切断時のイベントリスナー
    peer.on("disconnected", () => {
      resetState();
    });

    return () => {
      resetState();
      peer.destroy();
    };
  }, []); // コンポーネント初期化時に1回実行

  const connectToPeer = (opponentId: string) => {
    if (!peer) {
      return;
    }

    if (opponentId === peerId) {
      return;
    }

    const conn = peer.connect(opponentId);

    conn.on("open", () => {
      // 接続確立
    });
  };

  return { peer, peerId, isConnected, connectToPeer };
}
