import { z } from "zod";
import { PlayerSchema } from "./player.zod";

const StandPieceSchema = z.object({
  name: z.string(),
  type: z.string(),
  count: z.number().int().min(0),
});

export const StandSchema = z.record(PlayerSchema, z.array(StandPieceSchema));

export type Stand = z.infer<typeof StandSchema>;
export type StandPiece = z.infer<typeof StandPieceSchema>;
