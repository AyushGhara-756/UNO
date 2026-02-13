package org.th.GameLogic;

import org.th.Cards.Card;
import org.th.Cards.Color;

import java.util.List;
import java.util.Scanner;

public class GameEngine {

    private Deck playerDeck = null;
    private Deck computerDeck = null;
    private Card lastplayedCard = null;
    private static final Scanner sc = new Scanner(System.in);
    private static Deque<String> sequence = new ArrayDeque<>();

    public void start(){
        System.out.println("Game started");
        System.out.println("Welcome to UNO");
        playerDeck = new Deck();
        computerDeck = new Deck();
        lastplayedCard = new Card();
        sequence.offer("Player");
        sequence.offer("Computer");
        game();
    }

    public boolean game(){
        System.out.println("Starting card: ");
        lastplayedCard.printCard();

        // rewrite the logic

        System.out.println(playerDeck.getCards().isEmpty() ?
                "Congratulations! You won." : "Oops! You lost.");
        return playAgain();
    }

    private boolean isGameOver() {
        return playerDeck.getCards().isEmpty() || computerDeck.getCards().isEmpty();
    }

    public boolean playAgain(){
        System.out.print("Play again ? [y/n]: ");
        sc.nextLine();
        String play = sc.nextLine().toLowerCase().trim();
        if (play.equals("n"))
            return stop();
        else if (play.equals("y"))
            return game();
        else {
            System.out.println("Invalid input, try again");
            return playAgain();
        }
    }

    public boolean stop(){
        System.out.println("Game stopped. \nThank you for playing.");
        return false;
    }

    public Card inputCard(){
        playerDeck.printDeck();
        System.out.println("\nEnter color of card you want to play or type 'draw': ");
        String color = sc.next().toLowerCase();

        if (color.equals("draw")) {
            Card card = new Card();
            System.out.println("Card drew: "+card);
            System.out.println("\nWant to play it? [y/n]: ");
            String wantToPlay = sc.next().toLowerCase();

            if (wantToPlay.equals("y")) {
                return card;
            } else if (wantToPlay.equals("n")) {
                playerDeck.getCards().addLast(card);
                return null; // Return null to skip turn
            } else {
                System.out.println("Invalid input, try again");
                return inputCard();
            }
        } else {
            System.out.println("Enter the action on the card: ");
            String action = sc.next().toLowerCase().trim();
            return new Card(action, color);
        }
    }

    public void playerTurn(){
        Card play = inputCard();

        if (play == null) {
            return; // Skip turn if null
        }

        Card playedCard = playerDeck.playCard(lastplayedCard, play);
        if (playedCard == null){
            System.out.println("Invalid card entered, try again.");
            playerTurn();
            return;
        }
        lastplayedCard = playedCard;
    }

    public void computerTurn(){
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
                lastplayedCard = card;
            }
            else {
                System.out.println("He doesn't has a card to play");
            }
        } else {
            for (Card card : matchedCards) {
                if (card.getColor().equals(lastplayedCard.getColor()) ||
                        card.getAction().equals(lastplayedCard.getAction())) {
                    System.out.println("Computer played: "+card);
                    computerDeck.getCards().remove(card);
                    lastplayedCard = card;
                    break;
                }
            }
        }
    }
}