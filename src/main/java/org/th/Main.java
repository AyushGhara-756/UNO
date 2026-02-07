package org.th;

import org.th.GameLogic.Deck;
import org.th.GameLogic.GameEngine;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        boolean running = false;
        GameEngine engine = new GameEngine();
        Scanner input = new Scanner(System.in);

        engine.start();
        running = true;

        while (running) {
            boolean ask = engine.game();
            running = (ask) ? engine.playAgain(): true;
        }

    }
}
