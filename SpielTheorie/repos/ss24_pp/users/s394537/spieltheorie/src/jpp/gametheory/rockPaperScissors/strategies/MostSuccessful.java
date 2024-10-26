package jpp.gametheory.rockPaperScissors.strategies;

import jpp.gametheory.generic.IGameRound;
import jpp.gametheory.generic.IPlayer;
import jpp.gametheory.generic.IReward;
import jpp.gametheory.generic.IStrategy;
import jpp.gametheory.rockPaperScissors.RPSChoice;

import java.util.List;

public class MostSuccessful implements IStrategy<RPSChoice> {
    private IStrategy<RPSChoice> alternate;private IReward<RPSChoice> reward;
    public MostSuccessful(IStrategy<RPSChoice> alternate, IReward<RPSChoice> reward) {
        if(alternate== null || reward== null)throw new NullPointerException();
        this.alternate= alternate;
        this.reward= reward;
    }

    @Override
    public String name() {
        return "Most Successful Choice (Alternate: "+alternate.name()+")";
    }

    @Override
    public RPSChoice getChoice(IPlayer<RPSChoice> player, List<IGameRound<RPSChoice>> previousRounds) {
        int rock=0;
        int scissors=0;
        int paper=0;
        for(IGameRound<RPSChoice> round: previousRounds){
            for(IPlayer<RPSChoice> player1 : round.getPlayers()){
                if(round.getChoice(player1).equals(RPSChoice.ROCK)){
                    rock+= reward.getReward(player1,round) ;
                }else if(round.getChoice(player1).equals(RPSChoice.SCISSORS)){
                    scissors+= reward.getReward(player1,round);
                }else{
                    paper+=reward.getReward(player1,round);
                }
            }
        }
        if(rock> scissors && rock >paper)return RPSChoice.ROCK;
        else if(scissors>rock && scissors> paper)return RPSChoice.SCISSORS;
        else if(paper>scissors && paper>rock)return RPSChoice.PAPER;
        return alternate.getChoice(player,previousRounds);
    }

    @Override
    public String toString() {
        return  name();
    }
}
