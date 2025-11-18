import Link from "./Link";
import Input from "./Input";

export default function Peer({
  isConnected,
  peerId,
  connectToPeer,
}: {
  isConnected: boolean;
  peerId: string;
  connectToPeer: (opponentId: string) => void;
}) {
  if (!isConnected) return <p>Connecting...</p>;

  return (
    <>
      <Link peerId={peerId} />
      <Input connectToPeer={connectToPeer} />
    </>
  );
}
