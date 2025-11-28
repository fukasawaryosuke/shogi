export default function Turn({ turn }: { turn: string }) {
  return (
    <section className="centered-section">
      <h2>手番</h2>
      <div>{turn}</div>
    </section>
  );
}
