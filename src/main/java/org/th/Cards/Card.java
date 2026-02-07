package org.th.Cards;

import lombok.Data;

import java.util.Random;

@Data
public class Card{

    private Action action;
    private Color color;
    private static Color[] colors = Color.values();
    private static Action[] actions = Action.values();
    private Random rand = new Random();

    public Card() {
        action = actions[rand.nextInt(actions.length)];
        color = colors[rand.nextInt(colors.length)];
    }

    public void printCard(){
        System.out.print(color + ": " + action);
    }

}