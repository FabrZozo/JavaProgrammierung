package jpp.gametheory.rockPaperScissors.strategies;

import jpp.gametheory.generic.IGameRound;
import jpp.gametheory.generic.IPlayer;
import jpp.gametheory.generic.IStrategy;
import jpp.gametheory.rockPaperScissors.RPSChoice;

import java.util.List;


public class MostCommon implements IStrategy<RPSChoice> {
    private IStrategy<RPSChoice> alternate;
    public MostCommon(IStrategy<RPSChoice> alternate) {
        if(alternate== null)throw new NullPointerException();
        this.alternate= alternate;

    }

    @Override
    public String name() {
        return "Most Common Choice (Alternate: "+ alternate.name()+")";
    }

    @Override
    public RPSChoice getChoice(IPlayer<RPSChoice> player, List<IGameRound<RPSChoice>> previousRounds) {
        int rock=0;
        int scissors=0;
        int paper=0;
        for(IGameRound<RPSChoice> round: previousRounds){
            rock+= (int)round.getPlayers().stream().filter(e->round.getChoice(e).equals(RPSChoice.ROCK)).count();
            scissors+= (int)round.getPlayers().stream().filter(e->round.getChoice(e).equals(RPSChoice.SCISSORS)).count();
            paper+= (int)round.getPlayers().stream().filter(e->round.getChoice(e).equals(RPSChoice.PAPER)).count();
        }
        if(rock> scissors && rock >paper)return RPSChoice.ROCK;
        else if(scissors>rock && scissors> paper)return RPSChoice.SCISSORS;
        else if(paper>scissors && paper>rock)return RPSChoice.PAPER;
        return alternate.getChoice(player,previousRounds);
    }

    @Override
    public String toString() {
        return name();
    }
}
