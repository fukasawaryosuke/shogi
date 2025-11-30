export interface Exports extends WebAssembly.Exports {
  memory: WebAssembly.Memory;

  main(): void;
  getBoard(): number;
  getStand(): number;
  getTurn(): number;
  move(fromX: number, fromY: number, toX: number, toY: number): number;
  drop(pieceTypeNameLength: number, x: number, y: number): number;
  canChoosePromote(x: number, y: number): boolean;
  mustPromote(x: number, y: number): boolean;
  promote(x: number, y: number): void;
  isGameOver(): boolean;
  nextTurn(): void;

  getStringBufferPointer(): number; // バッファのメモリアドレス
  getStringBufferLength(): number; // 最後に書き込んだバイト長
}
