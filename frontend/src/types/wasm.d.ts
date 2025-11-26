import { Board } from "./schemas/board.zod";
import { Stand } from "./schemas/stand.zod";
import { Turn } from "./schemas/turn.zod";

export interface Exports extends WebAssembly.Exports {
  main(): void;
  getBoard(): Board;
  getStand(): Stand;
  getTurn(): Turn;
}
