package com.shogi.presentation.cli;

import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.piece.PieceType;
import com.shogi.application.usecase.Game;

public class App {
    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI();
        Game game = new Game();

        ui.showMessage("いざ、対局");
        while (true) {
            Player currentPlayer = game.getCurrentPlayer();
            if (game.isGameOver()) {
                break;
            }

            ui.showMessage("手番: " + currentPlayer);
            ui.display(game.getTurn(), game.getBoard(), game.getStand());

            ActionType action = ui.askAction();
            if (action == ActionType.MOVE) {
                Position from = ui.getFromPosition();
                Position to = ui.getToPosition();
                String error = game.move(from, to);
                if (error != null) {
                    ui.showMessage(error);
                    continue;
                }

                boolean mustPromote = game.mustPromote(to);
                if (mustPromote) {
                    game.promote(to);
                }

                boolean canChoosePromote = game.canChoosePromote(to);
                if (canChoosePromote) {
                    boolean shouldPromote = ui.askPromote();
                    if (shouldPromote) {
                        game.promote(to);
                    }
                }

                game.nextTurn();
            }

            if (action == ActionType.DROP) {
                PieceType pieceType = ui.getDropPieceType(game.getTurn().getCurrentPlayer());
                Position to = ui.getDropPosition();
                String error = game.drop(pieceType, to);

                if (error != null) {
                    ui.showMessage(error);
                    continue;
                }

                game.nextTurn();
            }
        }
        ui.showMessage("勝負あり");
    }
}
