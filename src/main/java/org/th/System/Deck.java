package org.th.System;

import lombok.Data;
import org.th.Cards.Action;
import org.th.Cards.Card;
import org.th.Cards.Color;
import org.th.Player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class Deck {

    private List<Card> cards = new ArrayList<>();

    public void generateDeck() {
        Color[] normalColors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};

        for (Color color : normalColors) {
            // ZERO — only one per color as real UNO
            cards.add(new Card(Action.ZERO, color));

            // ONE through DRAW2 — two copies each per color
            Action[] twice = {
                    Action.ONE, Action.TWO, Action.THREE, Action.FOUR,
                    Action.FIVE, Action.SIX, Action.SEVEN, Action.EIGHT,
                    Action.NINE, Action.SKIP, Action.REVERSE, Action.DRAW2,
                    Action.DRAW4
            };
            for (Action action : twice) {
                cards.add(new Card(action, color));
                cards.add(new Card(action, color));
            }
        }

        // WILD cards — 4 of each
        for (int i = 0; i < 4; i++) {
            cards.add(new Card(Action.CHANGE, Color.WILD));
            cards.add(new Card(Action.CHANGE4, Color.WILD));
        }

        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.removeFirst();
    }

    public void addCard(Card card) {
        cards.addLast(card);
    }

    public void distribute(List<Player> players) {
        for (int i = 0; i < 7; i++) {           // ✅ 7 cards each, standard UNO
            for (Player player : players) {
                player.getHand().addLast(drawCard());
            }
        }
    }
}