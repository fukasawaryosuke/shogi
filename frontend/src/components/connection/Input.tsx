import { useState } from "react";

export default function Input({
  connectToPeer,
}: {
  connectToPeer: (opponentId: string) => void;
}) {
  const [opponentId, setOpponentId] = useState("");

  const handleConnect = () => {
    if (opponentId.trim()) {
      connectToPeer(opponentId);
      setOpponentId("");
    }
  };

  return (
    <div className="peer-input">
      <input
        className="peer-input-field"
        type="text"
        value={opponentId}
        onChange={(e) => setOpponentId(e.target.value)}
        placeholder="opponent ID"
      />
      <button
        className="peer-button"
        onClick={handleConnect}
        disabled={!opponentId.trim()}
      >
        Connect
      </button>
    </div>
  );
}
