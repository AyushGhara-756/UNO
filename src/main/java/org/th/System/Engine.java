package org.th.System;

import org.th.Cards.Card;
import org.th.Player.Player;

import java.util.List;
import java.util.Random;

public class Engine {

    public Deck mainDeck = new Deck();
    public Deck stashDeck = new Deck();
    public List<Player> players;
    public int currentIndex = 0;
    public int direction = 1;
    public boolean running = false;

    public void start() {
        try {
            showLoadingAnimation("Starting game engine", 2000);
            System.out.println("Welcome to UNO!!!");
            showLoadingAnimation("Generating Deck", 2000);
            mainDeck.generateDeck();
            showLoadingAnimation("Distributing cards", 2000);
            players = List.of(new Player("Ayush", "user"), new Player("Computer", "computer"));
            mainDeck.distribute(players);
            running = true;
            run();
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }

    public void run() {
        currentIndex = new Random().nextInt(players.size());

        // First card on stash — skip wilds as starting card
        Card topCard;
        do {
            topCard = mainDeck.drawCard();
        } while (topCard.getColor().name().equals("WILD"));
        mainDeck.addCard(topCard);
        System.out.println("\nStarting card: " + topCard);

        while (running) {
            System.out.println("\n========================================");
            Player current = players.get(currentIndex);

            Card played = current.playTurn(topCard, mainDeck, stashDeck);

            // null means turn was passed — topCard stays the same
            if (played != null) {
                topCard = played;
            }

            // Win check
            if (current.hasWon()) {
                System.out.println("\n>> " + current.getName() + " WINS! GG!");
                running = false;
                break;
            }

            applyEffect(played);
        }
    }

    // ─────────────────────────────────────────────
    // TURN NAVIGATION
    // ─────────────────────────────────────────────

    private int nextIndex(int steps) {
        return (currentIndex + steps * direction + players.size() * Math.abs(steps)) % players.size();
    }

    private void advanceTurn(int steps) {
        currentIndex = nextIndex(steps);
    }

    // ─────────────────────────────────────────────
    // CARD EFFECTS
    // ─────────────────────────────────────────────

    private void applyEffect(Card played) {
        if (played == null) {
            advanceTurn(1); // turn passed, move on normally
            return;
        }

        switch (played.getAction()) {
            case SKIP -> {
                String skipped = players.get(nextIndex(1)).getName();
                System.out.println(">> SKIP! " + skipped + " is skipped.");
                advanceTurn(2);
            }
            case REVERSE -> {
                direction *= -1;
                System.out.println(">> REVERSE! Direction changed.");
                if (players.size() == 2) {
                    advanceTurn(2); // acts like skip with 2 players
                } else {
                    advanceTurn(1);
                }
            }
            case DRAW2 -> {
                Player target = players.get(nextIndex(1));
                System.out.println(">> +2! " + target.getName() + " draws 2 and is skipped.");
                target.drawCards(2, mainDeck);
                advanceTurn(2);
            }
            case DRAW4 -> {
                Player target = players.get(nextIndex(1));
                System.out.println(">> +4! " + target.getName() + " draws 4 and is skipped.");
                target.drawCards(4, mainDeck);
                advanceTurn(2);
            }
            case CHANGE4 -> {
                Player target = players.get(nextIndex(1));
                System.out.println(">> +4! " + target.getName() + " draws 4 and is skipped.");
                target.drawCards(4, mainDeck);
                advanceTurn(2);
                // color change already handled in playerTurn/computerTurn via handleWildColor
            }
            default -> advanceTurn(1);
        }
    }

    // ─────────────────────────────────────────────
    // LOADING ANIMATION
    // ─────────────────────────────────────────────

    private void showLoadingAnimation(String message, int durationMs) {
        int barLength = 20;
        long startTime = System.currentTimeMillis();
        try {
            while (System.currentTimeMillis() - startTime < durationMs) {
                long elapsed = System.currentTimeMillis() - startTime;
                int progress = (int) ((elapsed * barLength) / durationMs);
                String bar = "=".repeat(progress) + " ".repeat(barLength - progress);
                System.out.print("\r" + message + " [" + bar + "]");
                Thread.sleep(100);
            }
            System.out.println("\r" + message + " [" + "=".repeat(barLength) + "] Done!");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}