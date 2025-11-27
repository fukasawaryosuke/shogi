package com.shogi.wasm;

import org.teavm.interop.Address;
import org.teavm.interop.Export;
import java.nio.charset.StandardCharsets;

/**
 * WebAssembly線形メモリを使った文字列転送用のバッファクラス
 *
 * JavaのStringをUTF-8バイト配列に変換し、WASMメモリに書き込む
 * TypeScript側でポインタとバイト長を使って文字列を復元する
 */
public class StringBuffer {
  // 静的バッファのサイズ（8KB: Board用に拡張）
  private static final int BUFFER_SIZE = 8192;

  // 静的バッファ領域
  private static final byte[] buffer = new byte[BUFFER_SIZE];

  // 最後に書き込んだデータのバイト長
  private static int lastLength = 0;

  /**
   * 文字列をUTF-8バイト配列に変換してバッファに書き込む
   *
   * @param str 書き込む文字列
   * @return 書き込んだバイト数（バッファサイズを超えた場合は-1）
   */
  public static int writeString(String str) {
    if (str == null) {
      lastLength = 0;
      return 0;
    }

    try {
      // UTF-8バイト配列に変換
      byte[] bytes = str.getBytes(StandardCharsets.UTF_8);

      // バッファサイズを超える場合はエラー
      if (bytes.length > BUFFER_SIZE) {
        System.err.println("String too large for buffer: " + bytes.length + " bytes");
        return -1;
      }

      // バッファにコピー
      System.arraycopy(bytes, 0, buffer, 0, bytes.length);
      lastLength = bytes.length;

      return bytes.length;
    } catch (Exception e) {
      System.err.println("Error encoding string: " + e.getMessage());
      return -1;
    }
  }

  /**
   * バッファの先頭アドレスを取得
   * TypeScript側でメモリアクセスに使用
   *
   * @return バッファのメモリアドレス
   */
  @Export(name = "getStringBufferPointer")
  public static int getPointer() {
    return Address.ofData(buffer).toInt();
  }

  /**
   * 最後に書き込んだデータのバイト長を取得
   *
   * @return バイト長
   */
  @Export(name = "getStringBufferLength")
  public static int getLength() {
    return lastLength;
  }

  /**
   * バッファをクリア
   */
  public static void clear() {
    lastLength = 0;
  }
}
