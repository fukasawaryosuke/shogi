import { z } from "zod";
import { PlayerSchema } from "./player.zod";
import { PieceTypeSchema } from "./pieceType.zod";

export const StandSchema = z.record(
  PlayerSchema,
  z.record(PieceTypeSchema, z.number().int().min(0)).or(z.object({}))
);

export type Stand = z.infer<typeof StandSchema>;
