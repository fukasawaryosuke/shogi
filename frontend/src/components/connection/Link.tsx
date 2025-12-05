import { useState } from "react";

export default function Link({ peerId }: { peerId: string }) {
  const [isCopied, setIsCopied] = useState(false);

  const handleCopyPeerId = () => {
    navigator.clipboard.writeText(peerId);
    setIsCopied(true);
    setTimeout(() => setIsCopied(false), 1000);
  };

  return (
    <div className="peer-link">
      <p className="peer-id-text">Your ID: {peerId}</p>
      <button className="peer-button" onClick={handleCopyPeerId}>
        {isCopied ? "Copied!" : "Copy ID"}
      </button>
    </div>
  );
}
