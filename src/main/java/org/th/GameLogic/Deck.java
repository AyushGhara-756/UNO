package org.th.GameLogic;

import lombok.Data;
import org.th.Cards.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Data
public class Deck {

    private Card lastcardplayed = null;
    private List<Card> cards = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public Deck(){
        for (int i = 0; i<7; i++){
            cards.addLast(new Card());
        }
    }

    public void printDeck(){
        System.out.println("Your deck: ");
        cards.forEach(card -> System.out.print(card.getColor() + ":"+card.getAction()+" "));
    }

    public Card playCard(Card lastcardplayed, String color, String action) {
        if (lastcardplayed.getColor().toString().equals(color) ||
                lastcardplayed.getAction().toString().equals(action)
        ){
            List<Card> matchedCards = findCard(color, action);

            if (!matchedCards.isEmpty()) {
                Card played = matchedCards.get(0); //get the first match
                System.out.println("Card played: " + played.getColor()+ ": " + played.getAction());
                cards.remove(played); // remove the played card from deck
                return played;
            }
        }

        System.out.println("Invalid card entered, try again.");
        return null;
    }

    public List<Card> findCard(String color, String action){
        return cards
                .stream()
                .filter(c ->
                        c.getAction().toString().equals(action) &&
                                c.getColor().toString().equals(color)
                ) // filtered the matched cards out
                .toList();
    }

    public Card drawCard(){
        Card card = new Card();
        cards.addLast(card);
        return card;
    }
}
