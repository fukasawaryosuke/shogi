import { useEffect, useState } from "react";
import Board from "./components/Board";
import Stand from "./components/Stand";
import Turn from "./components/Turn";

export default function App() {
  const [state, setState] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetch(`/api/state`)
      .then((res) => {
        if (!res.ok) throw new Error(res.statusText);
        return res.json();
      })
      .then((data) => setState(data))
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;
  if (!state) return <div>No data</div>;

  const { board, stand, turn } = state;

  return (
    <div className="app-container">
      <h1>My Shogi (React)</h1>
      <Turn turn={turn} />
      <Stand stand={stand} />
      <Board board={board} />
      <Stand stand={stand} />
    </div>
  );
}
