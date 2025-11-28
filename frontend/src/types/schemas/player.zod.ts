import { z } from "zod";

export const PlayerSchema = z.enum(["先手", "後手"]);

export type Player = z.infer<typeof PlayerSchema>;
