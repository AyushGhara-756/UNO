package org.th;

import org.th.Cards.Card;
import org.th.Cards.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Deck playerDeck = new Deck(); //generated player's deck
        Deck computerDeck = new Deck(); //generated computer's deck

        playerDeck.playCard();

        while (!playerDeck.getCards().isEmpty() && !computerDeck.getCards().isEmpty()) {
            playerDeck.playCard(); // players playes a card
            System.out.println("Computer played: " + computerDeck.getCards().remove(computerDeck.getCards().size() - 1).getAction());
        }
        System.out.println("Congrats u won");
    }
}
