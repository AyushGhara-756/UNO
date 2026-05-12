package org.th.Player;

import lombok.Getter;
import lombok.Setter;
import org.th.Cards.Card;
import org.th.Cards.Color;
import org.th.System.Deck;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class Player {

    private String name;
    private List<Card> hand = new ArrayList<>();
    private String type; // "user" or "computer"

    public Player(String name, String type) {
        this.name = name;
        this.type = type;
    }

    // ─────────────────────────────────────────────
    // ENTRY POINT
    // ─────────────────────────────────────────────

    /**
     * Returns the card played, or null if the player drew and couldn't play.
     * Engine checks for null to know if turn was passed.
     */
    public Card playTurn(Card topCard, Deck mainDeck, Deck stashDeck) {
        System.out.println("\n>> " + name + "'s turn");
        System.out.println("Top card: " + topCard);

        if (type.equalsIgnoreCase("computer")) {
            return computerTurn(topCard, mainDeck, stashDeck);
        } else {
            return playerTurn(topCard, mainDeck, stashDeck);
        }
    }

    // ─────────────────────────────────────────────
    // USER TURN
    // ─────────────────────────────────────────────

    private Card playerTurn(Card topCard, Deck mainDeck, Deck stashDeck) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\nYour Cards:");
        for (int i = 0; i < hand.size(); i++) {
            System.out.print("[" + (i + 1) + "]" + hand.get(i) + "  ");
        }

        System.out.println("\nEnter [d] to Draw card OR position of card to play:");
        String choice = sc.nextLine().trim().toLowerCase();

        if (choice.equalsIgnoreCase("d")) {
            Card drew = mainDeck.drawCard();

            while (true) {
                System.out.println("You drew: " + drew + "\nWant to play it? [y/n]");
                choice = sc.nextLine().trim().toLowerCase();

                if (choice.equalsIgnoreCase("y")) {
                    if (drew.isPlayable(topCard)) {
                        handleWildColor(drew, sc);
                        stashDeck.addCard(drew);
                        System.out.println("You played: " + drew);
                        return drew;
                    } else {
                        System.out.println("That card can't be played. Adding to hand.");
                        hand.addLast(drew);
                        return null; // ✅ null = turn passed
                    }
                } else if (choice.equalsIgnoreCase("n")) {
                    System.out.println("Turn skipped. Card added to hand.");
                    hand.addLast(drew);
                    return null; // ✅ null = turn passed
                }
                // any other input → loop asks y/n again
            }

        } else {
            try {
                int cardNum = Integer.parseInt(choice) - 1;

                if (cardNum < 0 || cardNum >= hand.size()) {
                    System.out.println("Invalid position. Try again.");
                    return playerTurn(topCard, mainDeck, stashDeck);
                }

                Card played = hand.get(cardNum);

                if (!played.isPlayable(topCard)) {
                    System.out.println("Card is not playable. Try again.");
                    return playerTurn(topCard, mainDeck, stashDeck);
                }

                handleWildColor(played, sc);
                hand.remove(cardNum);
                stashDeck.addCard(played);
                System.out.println("You played: " + played);
                return played;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
                return playerTurn(topCard, mainDeck, stashDeck);
            }
        }
    }

    // ─────────────────────────────────────────────
    // COMPUTER TURN
    // ─────────────────────────────────────────────

    private Card computerTurn(Card topCard, Deck mainDeck, Deck stashDeck) {
        List<Card> playable = hand.stream()
                .filter(card -> card.isPlayable(topCard))
                .sorted(Comparator.comparing((Card card) -> card.getColor().ordinal())
                        .thenComparing(card -> card.getAction().ordinal()))
                .toList();

        if (playable.isEmpty()) {
            System.out.println(name + " drew a card.");
            Card drew = mainDeck.drawCard();

            if (drew.isPlayable(topCard)) {
                handleComputerWild(drew);
                stashDeck.addCard(drew);
                System.out.println(name + " played drawn card: " + drew);
                return drew;
            } else {
                hand.addLast(drew);
                System.out.println(name + " couldn't play. Turn passed.");
                return null; // ✅ null = turn passed
            }
        }

        Card played = playable.getFirst();
        hand.remove(played);
        handleComputerWild(played);
        stashDeck.addCard(played);
        System.out.println(name + " played: " + played);
        return played;
    }

    // ─────────────────────────────────────────────
    // WILD CARD HANDLING
    // ─────────────────────────────────────────────

    private void handleWildColor(Card card, Scanner sc) {
        if (card.getColor() != Color.WILD) return;

        Color[] valid = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
        System.out.println("Choose a color: [1]RED  [2]BLUE  [3]GREEN  [4]YELLOW");
        System.out.println("Enter the number");

        while (true) {
            String input = sc.nextLine().trim();
            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx >= 0 && idx < valid.length) {
                    card.setColor(valid[idx]);
                    System.out.println("Color set to: " + card.getColor());
                    return;
                }
                System.out.println("Enter 1-4.");
            } catch (NumberFormatException e) {
                System.out.println("Enter a number 1-4.");
            }
        }
    }

    private void handleComputerWild(Card card) {
        if (card.getColor() != Color.WILD) return;
        card.setColor(getMostCommonColor());
        System.out.println(name + " changed color to: " + card.getColor());
    }

    private Color getMostCommonColor() {
        return hand.stream()
                .filter(c -> c.getColor() != Color.WILD)
                .collect(Collectors.groupingBy(Card::getColor, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(Color.RED);
    }

    // ─────────────────────────────────────────────
    // HELPERS
    // ─────────────────────────────────────────────

    public void drawCards(int count, Deck mainDeck) {
        for (int i = 0; i < count; i++) {
            hand.addLast(mainDeck.drawCard());
        }
    }

    public boolean hasWon() {
        return hand.isEmpty();
    }
}