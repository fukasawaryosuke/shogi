import { z } from "zod";
import { PositionSchema } from "./position.zod";
import { PieceSchema } from "./piece.zod";

const BOARD_X_SIZE = 9;
const BOARD_Y_SIZE = 9;

export const BoardSchema = z
  .array(
    z
      .array(
        z.object({
          position: PositionSchema,
          piece: PieceSchema,
        })
      )
      .length(BOARD_X_SIZE)
  )
  .length(BOARD_Y_SIZE);

export type Board = z.infer<typeof BoardSchema>;
