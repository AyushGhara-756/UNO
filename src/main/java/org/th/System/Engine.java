package org.th.System;

import org.th.Cards.Card;
import org.th.GameLogic.Deck;

import java.util.ArrayList;
import java.util.List;

public class Engine {

    public Deck mainDeck = new Deck();
    public Deck stashDeck = new Deck();

    public void init(){
        mainDeck.generateDeck();
    }

}
