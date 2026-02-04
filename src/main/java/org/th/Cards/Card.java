package org.th.Cards;

import lombok.Data;

import java.util.Random;

@Data
public class Card{

    private Action action;
    private static Action[] actions = Action.values();
    private Random rand = new Random();

    public Card() {
        action = actions[rand.nextInt(actions.length)];
    }

}