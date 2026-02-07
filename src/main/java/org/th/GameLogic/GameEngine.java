package org.th.GameLogic;

import org.th.Cards.Card;

import java.util.List;
import java.util.Scanner;

public class GameEngine {


    private Deck playerDeck = null; //player's deck
    private Deck computerDeck = null; // computer's deck
    private Card lastplayedCard = null;
    private Scanner sc = new Scanner(System.in);

    public void start(){
        System.out.println("Game started");
        System.out.println("Welcome to UNO");
        playerDeck = new Deck(); // generated deck
        computerDeck = new Deck(); // generated deck
        lastplayedCard = new Card();
        game();
    }

    public void stop(){
        System.out.println("Game stopped");
    }

    public boolean game(){
        System.out.println("Starting card: ");
        lastplayedCard.printCard();
        playerTurn();
        computerTurn();
        if (playerDeck.getCards().isEmpty()){
            System.out.println("Congratulations! You won.");
            return true;
        }
        else if (computerDeck.getCards().isEmpty()){
            System.out.println("Oops! You lost.");
            return true;
        }
        else return game();
    }

    public boolean playAgain(){
        System.out.print("Play again ? [y/n]: ");
        String play = sc.nextLine().toLowerCase();
        if (play.equals("n")) return false;
        else if (play.equals("y")) return true;
        else {System.out.println("Invalid input, try again"); return playAgain();}
    }

    public void playerTurn(){

        System.out.println("Your turn: ");
        playerDeck.printDeck();
        System.out.println("Type draw to draw a card");
        System.out.println("Enter color of card you want to play: ");
        System.out.println("\nEnter color of card you want to play: ");
        String color = sc.next().toLowerCase(); // Player gives the color of the card
        if  (color.equals("draw")){
            System.out.println("Card drew: ");
            Card drewcard = playerDeck.drawCard();
            drewcard.printCard();
            System.out.println("Want to play it ? [y/n]");
            String choice = sc.nextLine().toLowerCase();
            if (choice.equals("y"))
                    playerDeck.playCard(lastplayedCard, drewcard.getColor().toString(),drewcard.getAction().toString());
            else if (choice.equals("n")) return ;
            else {System.out.println("Invalid choice"); playerTurn();}
        }
        else {
            System.out.println("\nEnter the action on the card");
            String action = sc.next(); // Player gives specifies action on the card
            lastplayedCard = playerDeck.playCard(lastplayedCard, color, action); // last played card
        }
        if (lastplayedCard == null){
            playerTurn();
        }

    }

    public void computerTurn(){
        List<Card> matchedcards =  computerDeck.getCards()
                .stream()
                .filter(card -> card.getColor().equals(lastplayedCard.getColor())|| card.getAction().equals(lastplayedCard.getAction()))
                .toList();

        for (Card card : matchedcards){
            if (card.getColor().equals(lastplayedCard.getColor())){
                card.printCard();
                computerDeck.getCards().remove(card);
                lastplayedCard = card;
                break;
            }
            else if (card.getAction().equals(lastplayedCard.getAction())){
                card.printCard();
                computerDeck.getCards().remove(card);
                lastplayedCard = card;
                break;
            }
        }
    }
}
