import type { Exports } from "../types/wasm";

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
    console: {
      log: (msg: string) => {
        console.log(msg);
      },
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

      return new Wasm(instance.exports as Exports);
    } catch (error) {
      console.error("Failed to initialize WASM:", error);
      throw error;
    }
  }

  main(): void {
    this.wasm.main();
  }
}
