export interface Exports extends WebAssembly.Exports {
  memory: WebAssembly.Memory;

  main(): void;
  getBoard(): number;
  getStand(): number;
  getTurn(): number;
  move(fromX: number, fromY: number, toX: number, toY: number): number;
  nextTurn(): void;

  getStringBufferPointer(): number; // バッファのメモリアドレス
  getStringBufferLength(): number; // 最後に書き込んだバイト長
}
