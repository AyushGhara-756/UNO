package org.th.Cards;

import lombok.Data;

import java.util.List;

@Data
public class Card {

    private Action action;
    private Color color;
    private static final List<Action> stackRule = List.of(Action.DRAW2,Action.DRAW4,Action.CHANGE4);

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

    public boolean isStackable(Card played) {
        if (!stackRule.contains(this.action)) return false;
        if (!stackRule.contains(played.getAction())) return false;

        int myPriority = stackRule.indexOf(this.action);
        int playedPriority = stackRule.indexOf(played.getAction());

        if (myPriority == playedPriority) return true;                        // same type → always allowed
        if (myPriority > playedPriority) return color.equals(Color.WILD);    // higher priority → only WILD
        return false;                                                          // lower priority → never
    }
}