import type { Exports } from "../types/wasm";
import type { Board } from "../types/schemas/board.zod";
import type { Stand } from "../types/schemas/stand.zod";
import type { Turn } from "../types/schemas/turn.zod";
import { BoardSchema } from "../types/schemas/board.zod";
import { StandSchema } from "../types/schemas/stand.zod";
import { PlayerSchema } from "../types/schemas/player.zod";

export class Wasm {
  private static readonly WASM_PATH = "/wasm/classes.wasm";
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

  /**
   * WASMメモリに文字列を書き込む
   *
   * @param str 書き込む文字列
   * @returns 書き込んだバイト長
   */
  private writeStringToMemory(str: string): number {
    if (!this.wasm.memory) {
      throw new Error("WASM memory is not available");
    }

    const encoder = new TextEncoder();
    const bytes = encoder.encode(str);
    const pointer = this.wasm.getStringBufferPointer();
    const memoryBuffer = new Uint8Array(this.wasm.memory.buffer);

    memoryBuffer.set(bytes, pointer);

    return bytes.length;
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

    try {
      const parsed = JSON.parse(jsonString);
      const board = BoardSchema.parse(parsed);
      return board;
    } catch (error) {
      console.error("Board validation error:", error);
      if (error instanceof Error && "errors" in error) {
        console.error("Zod errors:", (error as any).errors);
      }
      throw error;
    }
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

    try {
      const parsed = JSON.parse(jsonString);
      const stand = StandSchema.parse(parsed);
      return stand;
    } catch (error) {
      console.error("Stand validation error:", error);
      if (error instanceof Error && "errors" in error) {
        console.error("Zod errors:", (error as any).errors);
      }
      throw error;
    }
  }

  /**
   * ターン情報を文字列として取得
   * WASMメモリから文字列を読み取る
   *
   * @returns ターン情報の文字列
   */
  getTurn(): Turn {
    const length = this.wasm.getTurn();

    if (length < 0) {
      throw new Error("Failed to write string to buffer");
    }

    if (length === 0) {
      throw new Error("Turn data is empty");
    }

    const pointer = this.wasm.getStringBufferPointer();

    const string = this.readStringFromMemory(pointer, length);
    const turn = PlayerSchema.parse(string);

    return turn;
  }

  /**
   * 駒を移動する
   *
   * @param fromX 移動元X座標
   * @param fromY 移動元Y座標
   * @param toX 移動先X座標
   * @param toY 移動先Y座標
   * @returns エラーメッセージ（成功時は空文字列）
   */
  move(fromX: number, fromY: number, toX: number, toY: number): string {
    const length = this.wasm.move(fromX, fromY, toX, toY);

    if (length < 0) {
      throw new Error("Failed to execute move");
    }

    if (length === 0) {
      return ""; // 成功
    }

    const pointer = this.wasm.getStringBufferPointer();
    return this.readStringFromMemory(pointer, length);
  }

  /**
   * 持ち駒を打つ
   *
   * @param pieceType 駒の種類
   * @param x X座標
   * @param y Y座標
   * @returns エラーメッセージ（成功時は空文字列）
   */
  drop(pieceType: string, x: number, y: number): string {
    // 文字列をメモリに書き込む
    const length = this.writeStringToMemory(pieceType);

    // Javaのdrop関数を呼び出す（文字列の長さを渡す）
    const resultLength = this.wasm.drop(length, x, y);

    if (resultLength < 0) {
      throw new Error("Failed to execute drop");
    }

    if (resultLength === 0) {
      return ""; // 成功
    }

    // エラーメッセージを読み取る
    const pointer = this.wasm.getStringBufferPointer();
    return this.readStringFromMemory(pointer, resultLength);
  }

  /**
   * 駒が成ることができるかチェックする（任意成り）
   *
   * @param x X座標
   * @param y Y座標
   * @returns 成ることができる場合true
   */
  canChoosePromote(x: number, y: number): boolean {
    return this.wasm.canChoosePromote(x, y);
  }

  /**
   * 駒が成らなければならないかチェックする（必須成り）
   *
   * @param x X座標
   * @param y Y座標
   * @returns 成らなければならない場合true
   */
  mustPromote(x: number, y: number): boolean {
    return this.wasm.mustPromote(x, y);
  }

  /**
   * 駒を成る
   *
   * @param x X座標
   * @param y Y座標
   */
  promote(x: number, y: number): void {
    this.wasm.promote(x, y);
  }

  /**
   * ゲームが終了しているかチェックする
   *
   * @returns ゲームが終了している場合true（王将が取られた場合）
   */
  isGameOver(): boolean {
    return this.wasm.isGameOver();
  }

  /**
   * 次のターンに進む
   */
  nextTurn(): void {
    this.wasm.nextTurn();
  }
}
