package org.th.Cards;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Data
public class Deck {

    private List<Card> cards = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public Deck(){
        for (int i = 0; i<7; i++){
            cards.addLast(new Card());
        }
    }

    public void playCard() {
        while (true) {
            System.out.println("Your deck: ");
            cards.forEach(card -> System.out.print(card.getAction() + " "));
            System.out.println("\nEnter which card you want to play: ");

            String choice = sc.next(); // Player's card input

            List<Card> matchedCards = cards
                    .stream()
                    .filter(c -> c.getAction().toString().equals(choice)) // filtered the matched cards out
                    .toList();

            if (!matchedCards.isEmpty()) {
                Card played = matchedCards.get(0); // get the first match
                System.out.println("Card played: " + played.getAction());
                cards.remove(played); // remove the played card from deck
                break; // exit loop after successful play
            } else {
                System.out.println("Invalid card entered, try again.");
            }
        }
    }
}
