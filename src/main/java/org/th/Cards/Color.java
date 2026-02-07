package org.th.Cards;

public enum Color {
    RED("red"), BLUE("blue"), GREEN("green"), YELLOW("yellow"), WILD("wild");

    private String color;

    Color(String color){
        this.color = color;
    }

    public static Color fromValue(String value) {
        for (Color c : Color.values()) {
            if (c.toString().equalsIgnoreCase(value)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public String toString(){
        return color.toUpperCase();
    }
}