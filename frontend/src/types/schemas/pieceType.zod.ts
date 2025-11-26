import { z } from "zod";

export const PieceTypeSchema = z.enum([
  "歩",
  "香",
  "桂",
  "銀",
  "金",
  "角",
  "飛",
  "王",
  "玉",
  "と",
  "成香",
  "成桂",
  "成銀",
  "馬",
  "竜",
]);

export type PieceType = z.infer<typeof PieceTypeSchema>;
