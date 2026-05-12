package org.th.Cards;

import lombok.Data;

@Data
public class Card {

    private Action action;
    private Color color;

    public Card(Action action, Color color) {
        this.action = action;
        this.color = color;
    }

    @Override
    public String toString() {
        return color + ":" + action;
    }

    public boolean isPlayable(Card topCard) {
        if (this.color == Color.WILD) return true;                        // wilds always playable
        if (this.color == topCard.getColor()) return true;                // same color
        if (this.action == topCard.getAction()) return true;              // same action/number
        return false;
    }
}