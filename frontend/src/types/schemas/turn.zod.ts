// TODO: Define Turn type properly
// {
//   player: PlayerSchema; // 手番
//   turnNumber: number; // 何手目か
// }

export type Turn = import("./player.zod").Player;
