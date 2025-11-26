import { z } from "zod";

export const PlayerSchema = z.enum(["SENTE", "GOTE"]);

export type Player = z.infer<typeof PlayerSchema>;
