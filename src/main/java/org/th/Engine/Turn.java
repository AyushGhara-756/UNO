package org.th.Engine;

import org.th.Cards.Card;
import org.th.GameLogic.Deck;

import java.util.List;

public class Turn {

    private Deck playerDeck = null;
    private Deck computerDeck = null;

    public void init(){
        playerDeck = new Deck();
        computerDeck = new Deck();
    }

    public Card playerTurn(Card inputCard,Card lastplayedCard){

        if (inputCard == null) {
            return null; // Skip turn if null
        }

        Card playedCard = playerDeck.playCard(lastplayedCard, inputCard);
        if (playedCard == null){
            System.out.println("Invalid card entered, try again.");
//            playerTurn();
            return null;
        }
        else return playedCard;
//        swap(playedCard);
    }

    public Card computerTurn(Card lastplayedCard){
        List<Card> matchedCards = computerDeck.getCards()
                .stream()
                .filter(c -> c.getColor().equals(lastplayedCard.getColor())||
                        c.getAction().equals(lastplayedCard.getAction()))
                .toList();

        if (matchedCards.isEmpty()){
            Card card = new Card();
            System.out.println("Computer drew a card.");

            if (card.getColor().equals(lastplayedCard.getColor()) ||
                    card.getAction().equals(lastplayedCard.getAction())) {
                System.out.println("Computer played: "+card);
//                swap(card);
            }
            else {
                System.out.println("He doesn't has a card to play");
                computerDeck.getCards().addLast(card);
            }
                return card;
        } else {
            Card play = null;
            for (Card card : matchedCards) {
                if (card.getColor().equals(lastplayedCard.getColor()) ||
                        card.getAction().equals(lastplayedCard.getAction())) {
                    System.out.println("Computer played: "+card);
                    computerDeck.getCards().remove(card);
//                    swap(card);
                    play = card;
                    break;
                }
            }
            return play;
        }
    }
}
