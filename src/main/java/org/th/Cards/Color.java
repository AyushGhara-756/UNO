package org.th.Cards;

public enum Color{
    RED("red"), BLUE("blue"), GREEN("green"), YELLOW("yellow"), WILD("wild");

    private String color;
    Color(String color){
        this.color = color;
    }
    @Override
    public String toString(){
         return color.toUpperCase(); 
    }
}