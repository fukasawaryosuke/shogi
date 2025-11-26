import { z } from "zod";

export const PositionSchema = z.object({
  x: z.number().int().min(1).max(9),
  y: z.number().int().min(1).max(9),
});

export type Position = z.infer<typeof PositionSchema>;
