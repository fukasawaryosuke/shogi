export default function Stand({ stand }: { stand: Record<string, string> }) {
  const entries = Object.entries(stand || {}).sort();

  return (
    <section className="centered-section">
      <h2>Stand</h2>
      <ul>
        {entries.map(([k, v]) => (
          <li key={k}>
            {k} — {v}
          </li>
        ))}
      </ul>
    </section>
  );
}
