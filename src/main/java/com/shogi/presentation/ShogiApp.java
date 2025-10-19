package com.shogi.presentation;

import com.shogi.application.service.Game;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;

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

            Position from = ui.getFromPosition();
            Position to = ui.getToPosition();
            String error = game.play(from, to);
            if (error != null) {
                ui.showMessage(error);
            }
        }
        ui.showMessage("勝負あり");
    }
}
