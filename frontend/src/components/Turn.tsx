export default function Turn({ turn }: { turn: string }) {
  return (
    <section className="centered-section">
      <h2>Turn</h2>
      <div>{turn}</div>
    </section>
  );
}
