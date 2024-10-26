package jpp.gametheory.rockPaperScissors;

import jpp.gametheory.generic.IGameRound;
import jpp.gametheory.generic.IPlayer;
import jpp.gametheory.generic.IReward;
import jpp.gametheory.generic.Player;

public class RPSReward implements IReward<RPSChoice> {

    @Override
    public int getReward(IPlayer<RPSChoice> player, IGameRound<RPSChoice> gameRound) {
        if(player== null || gameRound== null)throw new NullPointerException();
        if(!gameRound.getPlayerChoices().containsKey(player))throw new IllegalArgumentException();
        int rendit=0;
        switch (gameRound.getChoice(player)){
            case PAPER :
                for(IPlayer<RPSChoice> player1: gameRound.getOtherPlayers(player)){
                    if(gameRound.getPlayerChoices().get(player1).equals(RPSChoice.ROCK)){
                        rendit+=2;
                    }else if(gameRound.getPlayerChoices().get(player1).equals(RPSChoice.SCISSORS)){
                        rendit-=1;
                    }
                }
                break;

            case ROCK :
                for(IPlayer<RPSChoice> player1: gameRound.getOtherPlayers(player)){
                    if(gameRound.getPlayerChoices().get(player1).equals(RPSChoice.PAPER)){
                        rendit-=1;
                    }else if(gameRound.getPlayerChoices().get(player1).equals(RPSChoice.SCISSORS)){
                        rendit+=2;
                    }
                }
                break;
            case SCISSORS :
                for(IPlayer<RPSChoice> player1: gameRound.getOtherPlayers(player)){
                    if(gameRound.getPlayerChoices().get(player1).equals(RPSChoice.PAPER)){
                        rendit+=2;
                    }else if(gameRound.getPlayerChoices().get(player1).equals(RPSChoice.ROCK)){
                        rendit-=1;
                    }
                }
                break;
        }
        return rendit;
    }
}
