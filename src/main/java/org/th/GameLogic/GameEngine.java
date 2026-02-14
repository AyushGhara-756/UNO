package org.th.GameLogic;

import org.th.Cards.Action;
import org.th.Cards.Card;

import java.util.*;

public class GameEngine extends Thread {

    private Deck playerDeck = null;
    private Deck computerDeck = null;
    private Card lastplayedCard = null;
    private Card secondLastPlayedCard = null;
    static final Scanner sc = new Scanner(System.in);
    private static final Deque<String> sequence = new ArrayDeque<>();
    String turn = "";
    private volatile boolean running = true;

    public void swap(Card playedCard) {
        secondLastPlayedCard = lastplayedCard;
        lastplayedCard = playedCard;
    }

    public void initialize(){

        try {
            showLoadingAnimation("Starting game engine",2000);
            System.out.println("Welcome to UNO!!!");
            playerDeck = new Deck();
            showLoadingAnimation("Generating Player Deck",2000);
            computerDeck = new Deck();
            showLoadingAnimation("Generating Computer Deck",2000);
            lastplayedCard = new Card();

            sequence.clear();
            sequence.offer("Player");
            sequence.offer("Computer");

            running = true;
        }
        catch (Exception e){
            System.err.println("Error occurred: " + e.getMessage());
        }

    }

    /* Write the draw logic and starting card restrictions */

    @SuppressWarnings("BusyWait")
    @Override
    public void run(){
        initialize();
        System.out.println("Starting card: "+lastplayedCard);

        while(!isGameOver() && running){
            try {
                Thread.sleep(1000);
                turn = sequence.poll();                                 // get the turn from sequence

                System.out.println("\nLast card played: " + ((lastplayedCard!=null)?lastplayedCard:secondLastPlayedCard));// Print what the last card played
                if (lastplayedCard != null && lastplayedCard.getAction().equals(Action.SKIP)) {
                    sequence.offer(turn);
                    turn = sequence.poll();
                    if (turn != null && turn.equals("Player")) playerTurn();
                    else computerTurn();
                } else {
                    if (turn != null && turn.equals("Player")) playerTurn();
                    else computerTurn();
                }

                if (lastplayedCard.getAction().equals(Action.REVERSE)) {  // checks if the card played is reverse
                    List<String> seq = new ArrayList<>(sequence);       // creates a list out of sequence
                    Collections.reverse(seq);                           // reverse the list
                    sequence.clear();                                   // clear the sequence before adding something
                    sequence.add(turn);                                 // add current turn first
                    sequence.addAll(seq);                               // then add the reversed sequence
                } else {
                    sequence.offer(turn);                               // put the turn back to sequence normally
                }
            }catch (InterruptedException e){
                running = false;
                System.out.println("Error occurred: "+e.getMessage());
            }
        }

        System.out.println(playerDeck.getCards().isEmpty() ?
                "Congratulations! You won." : "Oops! You lost.");
        playAgain();
    }

    private boolean isGameOver() {
        return playerDeck.getCards().isEmpty() || computerDeck.getCards().isEmpty();
    }

    public void playAgain(){

        System.out.print("Play again ? [y/n]: ");
        String play = sc.nextLine().toLowerCase().trim();

        if (play.equals("n")) exit();
        else if (play.equals("y")) initialize();
        else {
            System.out.println("Invalid input, try again");
            playAgain();
        }

    }

    public void exit(){
        System.out.println("Game stopped. \nThank you for playing.");
        currentThread().interrupt();
        running = false;
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
                playerDeck.getCards().addLast(card);
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
        swap(playedCard);
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
                swap(card);
            }
            else {
                System.out.println("He doesn't has a card to play");
                computerDeck.getCards().addLast(card);
            }
        } else {
            for (Card card : matchedCards) {
                if (card.getColor().equals(lastplayedCard.getColor()) ||
                        card.getAction().equals(lastplayedCard.getAction())) {
                    System.out.println("Computer played: "+card);
                    computerDeck.getCards().remove(card);
                    swap(card);
                    break;
                }
            }
        }
    }
    private void showLoadingAnimation(String message, int durationMs) {
        int barLength = 20;
        long startTime = System.currentTimeMillis();

        try {
            while (System.currentTimeMillis() - startTime < durationMs) {
                long elapsed = System.currentTimeMillis() - startTime;
                int progress = (int) ((elapsed * barLength) / durationMs);

                String bar = "=".repeat(progress) + " ".repeat(barLength - progress);
                System.out.print("\r" + message + " [" + bar + "]");
                Thread.sleep(100);
            }
            System.out.println("\r" + message + " [" + "=".repeat(barLength) + "] Done!");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}