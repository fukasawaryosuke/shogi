import { Board } from "./schemas/board.zod";
import { Stand } from "./schemas/stand.zod";
import { Turn } from "./schemas/turn.zod";

export interface Exports extends WebAssembly.Exports {
  memory: WebAssembly.Memory;

  main(): void;
  getBoard(): number; // JSON文字列のバイト数を返す
  getStand(): number; // JSON文字列のバイト数を返す
  getTurn(): number; // JSON文字列のバイト数を返す

  getStringBufferPointer(): number; // バッファのメモリアドレス
  getStringBufferLength(): number; // 最後に書き込んだバイト長
}
