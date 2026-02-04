package org.th.Cards;

public enum Color{
    RED("Red"), BLUE("Blue"), GREEN("Green"), YELLOW("Yellow"), WILD("Wild");

    private String color;
    private Color(String color){
        this.color = color;
    }
}