/**
 * 将棋駒の画像アセット管理クラス
 * 駒の画像ファイルパスの解決を担当
 */
export class PieceAssetResolver {
  /**
   * 駒の画像ファイル名マッピング
   */
  private static readonly PIECE_FILE_MAP: Record<string, string> = {
    歩: "fu_hyou.svg",
    香: "kyo_sha.svg",
    桂: "kei_ma.svg",
    銀: "gin_sho.svg",
    金: "kin_sho.svg",
    角: "kaku_gyou.svg",
    飛: "hi_sha.svg",
    王: "ou_sho.svg",
    玉: "gyoku_sho.svg",
    と: "to.svg",
    成香: "nari_kyo.svg",
    成桂: "nari_kei.svg",
    成銀: "nari_gin.svg",
    馬: "ryu_ma.svg",
    竜: "ryu_ou.svg",
  };

  /**
   * プレイヤーとディレクトリ名のマッピング
   */
  private static readonly PLAYER_DIR_MAP: Record<string, string> = {
    先手: "sente",
    後手: "gote",
  };

  /**
   * 駒の画像URLを取得
   *
   * @param pieceName 駒の名前（例: "歩", "金"）
   * @param player プレイヤー（"先手" または "後手"）
   * @returns 画像のURL、存在しない場合はnull
   */
  static getImageUrl(pieceName: string, player: string): string | null {
    const dir = this.PLAYER_DIR_MAP[player];
    const file = this.PIECE_FILE_MAP[pieceName];

    if (!dir || !file) return null;

    return new URL(`../assets/${dir}/${file}`, import.meta.url).href;
  }
}
