package org.th.Cards;

import lombok.Data;

import java.util.Arrays;
import java.util.Random;

@Data
public class Card{

    private Action action;
    private Color color;
    static Color[] colors = Color.values();
//    static Action[] actions = Action.values();
    static Action[] numbers = Arrays
            .stream(Action.values())
            .filter(card -> (card != Action.CHANGE) && (card != Action.CHANGE4))
            .toArray(Action[]::new);
    static Action[] actions = new Action[]{Action.CHANGE, Action.CHANGE4};
    Random rand = new Random();

    public Card() {
        color = colors[rand.nextInt(colors.length)];
        if (color.equals(Color.WILD)) action = actions[rand.nextInt(actions.length)];
        else action = numbers[rand.nextInt(numbers.length)];
//        action = actions[rand.nextInt(actions.length)];
    }

    public Card(String action, String color) {
        this.action = Action.fromAction(action);
        this.color = Color.fromValue(color);
    }

    @Override
    public String toString(){
        return color + ":" + action;
    }

}