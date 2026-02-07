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
        System.out.println("\nYour deck: ");
        cards.forEach(card -> System.out.print(card.getColor() + ":"+card.getAction()+" "));
    }

    public Card playCard(Card lastPlayedCard, Card inputCard) {
        if (lastPlayedCard.getColor().equals(inputCard.getColor()) ||
                lastPlayedCard.getAction().equals(inputCard.getAction())
        ){
            List<Card> matchedCards = findCard(inputCard); // Search for the card in the deck

            if (!matchedCards.isEmpty()) {
                Card played = matchedCards.getFirst(); // Gets the first match
                System.out.println("Card played: " + played.getColor()+ ": " + played.getAction());
                cards.remove(played); // remove the played card from deck
                return played;
            }
        }
        return null;
    }

    //Function to search for the card
    public List<Card> findCard(Card inputCard){
        return cards
                .stream()
                .filter(c ->
                        c.getAction().equals(inputCard.getAction()) &&
                                c.getColor().equals(inputCard.getColor())
                ) // filtered the matched cards out
                .toList();
    }

    // Function to draw a new card.
    public Card drawCard(){
        Card card = new Card();
        cards.addLast(card);
        return card;
    }
}
