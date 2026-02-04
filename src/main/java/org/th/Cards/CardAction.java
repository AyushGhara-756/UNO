package org.th.Cards;

import java.util.Random;

public class CardAction {

    private Action[] action = Action.values();
    private Random rand = new Random();

    public Action setAction(){
        return action[rand.nextInt(action.length)];
    }

}
