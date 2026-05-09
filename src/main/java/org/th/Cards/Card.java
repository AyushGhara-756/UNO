package org.th.Cards;

import lombok.Data;

import java.util.Arrays;
import java.util.Random;

@Data
public class Card{

    private Action action;
    private Color color;

    @Override
    public String toString(){
        return color + ":" + action;
    }

    public Card(Action action, Color color) {
        this.action = action;
        this.color = color;
    }

    public boolean isPlayable(Card topCard){
        return (topCard.getColor().equals(color) || topCard.getAction().equals(action));
    }
}