package org.th.Player;

import lombok.Getter;
import lombok.Setter;
import org.th.Cards.Card;
import org.th.GameLogic.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Getter
@Setter
public class Player {

    private List<Card> hand = new ArrayList<>();
    private String type;

    public Card playTurn(Card topCard, Deck mainDeck, Deck stashDeck){
        System.out.println(type+" turn");
        System.out.println("Top card = "+ topCard.toString());

        if (type.equalsIgnoreCase("computer")) return computerTurn(topCard,mainDeck,stashDeck);
        else return playerTurn(topCard,mainDeck,stashDeck);
    }

    public Card playerTurn(Card topCard, Deck mainDeck, Deck stashDeck){
        System.out.println("\nYour Cards: ");
        hand.forEach(card -> System.out.print(card+" "));

        System.out.println("Enter [d] to Draw card \nor ");
        System.out.println("Enter position of the card (from left) to play: ");

        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine().trim().toLowerCase();

        if (choice.equalsIgnoreCase("d")){
            Card drew = mainDeck.drawCard();

            while(true) {
                System.out.println("You drew: " + drew.toString() + "\n want to play it [y/n]?");
                choice = sc.nextLine().trim().toLowerCase();
                if (choice.equalsIgnoreCase("y") && drew.isPlayable(topCard)) {
                    System.out.println("You Played: " + drew.toString());
                    stashDeck.addCard(drew);

                    return drew;
                } else if (choice.equalsIgnoreCase("n")) {
                    hand.addLast(drew);
                    return topCard;
                }
            }
        }

        int cardNum = Integer.parseInt(choice)-1;
        if (cardNum > hand.size() || cardNum < 0) {
            System.out.println("Invalid card positon. Please Try Again");
            playerTurn(topCard,mainDeck,stashDeck);
        }

        Card played = hand.get(cardNum);
        if (!played.isPlayable(topCard)) {
            System.out.println("Card is not playable. Please Try Again");
            playerTurn(topCard,mainDeck,stashDeck);
        }

        stashDeck.addCard(played);
        return hand.remove(cardNum);
    }

    public Card computerTurn(Card topCard, Deck mainDeck, Deck stashDeck){
        return null;
    }
}
