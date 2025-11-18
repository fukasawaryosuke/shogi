import { useState } from "react";

export default function Link({ peerId }: { peerId: string }) {
  const [isCopied, setIsCopied] = useState(false);

  const handleCopyPeerId = () => {
    navigator.clipboard.writeText(peerId);
    setIsCopied(true);
    setTimeout(() => setIsCopied(false), 1000);
  };

  return (
    <>
      <p>Your ID: {peerId}</p>
      <button onClick={handleCopyPeerId}>
        {isCopied ? "Copied!" : "Copy ID"}
      </button>
    </>
  );
}
