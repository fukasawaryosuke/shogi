import { z } from "zod";
import { PlayerSchema } from "./player.zod";
import { PieceNameSchema } from "./PieceName.zod";

export const PieceSchema = z.object({
  name: PieceNameSchema,
  owner: PlayerSchema,
});
export type Piece = z.infer<typeof PieceSchema>;
