import type { Exports } from "../types/wasm";
import type { Board } from "../types/schemas/board.zod";
import type { Stand } from "../types/schemas/stand.zod";
import type { Turn } from "../types/schemas/turn.zod";

export class Wasm {
  private static readonly WASM_PATH = "dist/classes.wasm";
  private static readonly IMPORTS: WebAssembly.Imports = {
    teavm: {
      logString: (str: string) => {
        console.log(str);
      },
      logInt: (num: number) => {
        console.log(num);
      },
      logOutOfMemory: () => {
        console.error("WASM: Out of memory");
      },
      currentTimeMillis: () => Date.now(),
    },
  };

  private wasm: Exports;

  private constructor(wasm: Exports) {
    this.wasm = wasm;
  }

  static async init(): Promise<Wasm> {
    try {
      const { instance } = await WebAssembly.instantiateStreaming(
        fetch(Wasm.WASM_PATH),
        Wasm.IMPORTS
      );

      console.log(instance.exports);

      return new Wasm(instance.exports as Exports);
    } catch (error) {
      console.error("Failed to initialize WASM:", error);
      throw error;
    }
  }

  main(): void {
    this.wasm.main();
  }

  getBoard(): Board {
    return this.wasm.getBoard();
  }

  getStand(): Stand {
    return this.wasm.getStand();
  }

  getTurn(): Turn {
    return this.wasm.getTurn();
  }
}
