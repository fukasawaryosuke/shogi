import "../styles/Turn.css";

export default function Turn({ turn }: { turn: string }) {
  return (
    <div className="turn-indicator">
      <span className="turn-label">{turn}</span>
    </div>
  );
}
