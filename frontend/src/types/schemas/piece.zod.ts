import { z } from "zod";
import { PlayerSchema } from "./player.zod";
import { PieceTypeSchema } from "./pieceType.zod";

export const PieceSchema = z.object({
  name: PieceTypeSchema,
  owner: PlayerSchema,
});
export type Piece = z.infer<typeof PieceSchema>;
