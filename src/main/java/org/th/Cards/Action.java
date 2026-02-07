package org.th.Cards;

public enum Action {
    ONE("1"),TWO("2"),THREE("3"),FOUR("4"),FIVE("5"),
    SIX("6"),SEVEN("7"),EIGHT("8"),NINE("9");

    private final String value;

    Action(String value) {
        this.value = value;
    }

    public static Action fromAction(String value) {
        for (Action a : Action.values()) {
            if (a.value.equals(value)) {
                return a;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
