package org.th.GameLogic;

import lombok.Data;
import org.th.Cards.Action;
import org.th.Cards.Card;
import org.th.Cards.Color;

import java.util.*;

@Data
public class Deck {

    private List<Card> cards = new ArrayList<>();

    public void generateDeck() {

        Color[] normalColors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};

        for (Color color : normalColors) {
            // ZERO through NINE, SKIP, REVERSE, DRAW2 — two copies each per color
            Action[] twice = {
                    Action.ZERO, Action.ONE, Action.TWO,
                    Action.THREE, Action.FOUR, Action.FIVE,
                    Action.SIX, Action.SEVEN, Action.EIGHT,
                    Action.NINE, Action.SKIP, Action.REVERSE, Action.DRAW2
            };
            for (Action action : twice) {
                cards.add(new Card(action, color));
                cards.add(new Card(action, color));
            }
        }
        // WILD cards — 4 of each, color is WILD
        for (int i = 0; i < 4; i++) {
            cards.add(new Card(Action.CHANGE, Color.WILD));
            cards.add(new Card(Action.CHANGE4, Color.WILD));
        }
        Collections.shuffle(cards);
    }

    public Card drawCard(){
        return cards.removeFirst();
    }

    public void addCard(Card played){
        cards.addLast(played);
    }

}