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
      // 標準出力・標準エラー出力用の関数を追加
      putwcharsOut: (charPtr: number) => {
        // 文字列を標準出力に書き込む（デバッグ用）
        console.log(String.fromCharCode(charPtr));
      },
      putwcharsErr: (charPtr: number) => {
        // 文字列を標準エラー出力に書き込む（デバッグ用）
        console.error(String.fromCharCode(charPtr));
      },
    },
  };

  private wasm: Exports;
  private textDecoder: TextDecoder;

  private constructor(wasm: Exports) {
    this.wasm = wasm;
    this.textDecoder = new TextDecoder("utf-8");
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

  /**
   * WASMメモリから文字列を読み取る
   *
   * @param pointer メモリアドレス
   * @param length バイト長
   * @returns デコードされた文字列
   */
  private readStringFromMemory(pointer: number, length: number): string {
    if (!this.wasm.memory) {
      throw new Error("WASM memory is not available");
    }

    const memoryBuffer = new Uint8Array(this.wasm.memory.buffer);
    const bytes = memoryBuffer.slice(pointer, pointer + length);
    return this.textDecoder.decode(bytes);
  }

  main(): void {
    this.wasm.main();
  }

  /**
   * ボード情報をJSON文字列として取得し、パースして返す
   * WASMメモリから文字列を読み取る
   *
   * @returns Board情報
   */
  getBoard(): Board {
    const length = this.wasm.getBoard();

    if (length < 0) {
      throw new Error("Failed to write board string to buffer");
    }

    if (length === 0) {
      throw new Error("Board data is empty");
    }

    const pointer = this.wasm.getStringBufferPointer();
    const jsonString = this.readStringFromMemory(pointer, length);

    return JSON.parse(jsonString) as Board;
  }

  /**
   * 持ち駒情報をJSON文字列として取得し、パースして返す
   * WASMメモリから文字列を読み取る
   *
   * @returns Stand情報
   */
  getStand(): Stand {
    const length = this.wasm.getStand();

    if (length < 0) {
      throw new Error("Failed to write stand string to buffer");
    }

    if (length === 0) {
      throw new Error("Stand data is empty");
    }

    const pointer = this.wasm.getStringBufferPointer();
    const jsonString = this.readStringFromMemory(pointer, length);

    return JSON.parse(jsonString) as Stand;
  }

  /**
   * ターン情報を文字列として取得
   * WASMメモリから文字列を読み取る
   *
   * @returns ターン情報の文字列
   */
  getTurn(): string {
    const length = this.wasm.getTurn();

    if (length < 0) {
      throw new Error("Failed to write string to buffer");
    }

    if (length === 0) {
      return "";
    }

    // バッファのポインタを取得
    const pointer = this.wasm.getStringBufferPointer();

    // メモリから文字列を読み取る
    return this.readStringFromMemory(pointer, length);
  }
}
