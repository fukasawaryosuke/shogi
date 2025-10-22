package com.shogi.presentation;

import com.shogi.application.service.Game;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.PieceType;

public class ShogiApp {
    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI();
        Game game = new Game();

        ui.showMessage("いざ、対局");
        while (!game.isGameOver()) {
            ui.displayTurn(game.getTurn());
            ui.displayStand(game.getStand(), Player.GOTE);
            ui.displayBoard(game.getBoard());
            ui.displayStand(game.getStand(), Player.SENTE);

            ActionType action = ui.askAction();
            if (action == ActionType.MOVE) {
                Position from = ui.getFromPosition();
                Position to = ui.getToPosition();
                String error = game.move(from, to);
                if (error != null) {
                    ui.showMessage(error);
                }
            }
            if (action == ActionType.DROP) {
                PieceType pieceType = ui.getDropPieceType(game.getTurn().getCurrentPlayer());
                Position to = ui.getToPosition();
                String error = game.drop(pieceType, to);
                if (error != null) {
                    ui.showMessage(error);
                }
                continue;
            }
        }
        ui.showMessage("勝負あり");
    }
}
