package com.shogi.presentation;

import com.shogi.application.service.Game;
import com.shogi.domain.valueobject.Position;

public class ShogiApp {
    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI();
        Game game = new Game();

        ui.showMessage("いざ、対局");
        while (!game.isGameOver()) {
            ui.displayTurn(game.getTurn());
            ui.displayBoard(game.getBoard());
            ui.displayStand(game.getStand());

            Position from = ui.getFromPosition();
            Position to = ui.getToPosition();
            String error = game.movePiece(from, to);
            if (error != null) {
                ui.showMessage(error);
            }
        }
        ui.showMessage("勝負あり");
    }
}
