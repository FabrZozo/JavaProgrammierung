package jpp.gametheory.rockPaperScissors.strategies;

import jpp.gametheory.generic.IGameRound;
import jpp.gametheory.generic.IPlayer;
import jpp.gametheory.generic.IStrategy;
import jpp.gametheory.rockPaperScissors.RPSChoice;

import java.util.List;


public class CircleChoice implements IStrategy<RPSChoice> {

    @Override
    public String name() {
        return "Circle Choice";
    }

    @Override
    public RPSChoice getChoice(IPlayer<RPSChoice> player, List<IGameRound<RPSChoice>> previousRounds) {
        if(player== null || previousRounds== null)throw new NullPointerException();
        if(previousRounds.isEmpty())return RPSChoice.ROCK;
        if(previousRounds.getLast().getChoice(player).equals(RPSChoice.ROCK)){
            return RPSChoice.PAPER;
        }else if(previousRounds.getLast().getChoice(player).equals(RPSChoice.PAPER)){
            return RPSChoice.SCISSORS;
        }else{
            return RPSChoice.ROCK;
        }
    }

    @Override
    public String toString() {
        return name();
    }
}
