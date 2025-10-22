package com.shogi.presentation;

import com.shogi.domain.entity.Board;
import com.shogi.domain.entity.Stand;
import com.shogi.domain.valueobject.Turn;
import com.shogi.domain.valueobject.Position;
import com.shogi.domain.valueobject.Player;
import com.shogi.domain.valueobject.PieceType;

import java.util.Scanner;

public class ConsoleUI {
  private Scanner scanner = new Scanner(System.in);

  public void displayTurn(Turn turn) {
    System.out.print("\n\n");
    System.out.println("手番: " + turn.toString());
  }

  public void displayBoard(Board board) {
    System.out.println();
    System.out.println(board.toString());
  }

  public void displayStand(Stand stand, Player player) {
    System.out.println();
    System.out.println("持ち駒: " + stand.toString(player));
  }

  public ActionType askAction() {
    System.out.println();
    System.out.print("アクションを選択してください (1: 駒を動かす, 2: 駒を置く): ");
    String input = scanner.nextLine().trim();
    ActionType action = ActionType.fromCode(input);
    if (action != null) {
      return action;
    } else {
      System.out.println("無効な選択です");
      return askAction();
    }
  }

  public PieceType getDropPieceType(Player player) {
    System.out.println();
    System.out.print(player + "の持ち駒から駒を選択してください (例: FU, KY, KE, GI, KI, KA, HI): ");
    String input = scanner.nextLine().trim().toUpperCase();
    try {
      return PieceType.valueOf(input);
    } catch (IllegalArgumentException e) {
      System.out.println("無効な駒の種類です");
      return getDropPieceType(player);
    }
  }

  public Position getFromPosition() {
    String prompt = "移動元を入力してください (例: 3 7): ";
    return inputPosition(prompt);
  }

  public Position getToPosition() {
    String prompt = "移動先を入力してください (例: 3 6): ";
    return inputPosition(prompt);
  }

  private Position inputPosition(String prompt) {
    System.out.println();
    System.out.print(prompt);
    String[] tokens = scanner.nextLine().trim().split("\\s+");
    if (tokens.length != 2 || !tokens[0].matches("-?\\d+") || !tokens[1].matches("-?\\d+")) {
      System.out.println("入力形式が正しくありません");
      return inputPosition(prompt);
    }
    int col = Integer.parseInt(tokens[0]);
    int row = Integer.parseInt(tokens[1]);
    return new Position(col, row);
  }

  // 成りたいかの確認
  public boolean askPromotion() {
    System.out.print("成りますか？ (y/n): ");
    String input = scanner.nextLine().trim().toLowerCase();
    return input.equals("y");
  }

  // メッセージ表示
  public void showMessage(String message) {
    System.out.println(message);
  }
}
