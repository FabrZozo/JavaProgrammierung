package jpp.gametheory.generic;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GameRound<C extends IChoice> implements IGameRound<C> {
    private Map<IPlayer<C>, C> playerChoices;
    public GameRound(Map<IPlayer<C>, C> playerChoices) {
        if(playerChoices== null)throw new NullPointerException();
        if(playerChoices.isEmpty())throw new IllegalArgumentException();
        this.playerChoices=playerChoices;

    }

    @Override
    public Map<IPlayer<C>, C> getPlayerChoices() {
        return playerChoices;
    }

    @Override
    public C getChoice(IPlayer<C> player) {
        if(player== null)throw new NullPointerException();
        return playerChoices.keySet().stream().filter(e->e.equals(player)).map(e->playerChoices.get(e)).findAny().orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Set<IPlayer<C>> getPlayers() {
        return Collections.unmodifiableSet(playerChoices.keySet());
    }

    @Override
    public Set<IPlayer<C>> getOtherPlayers(IPlayer<C> player) {
        if(player== null)throw new NullPointerException();
        if(!playerChoices.containsKey(player))throw new IllegalArgumentException();
        return playerChoices.keySet().stream().filter(e->!e.equals(player)).collect(Collectors.toSet());
    }
    //"(<Spielername 1> -> <Choice-Name 1>, <Spielername 2> -> <Choice-Name 2>, ...)"
    @Override
    public String toString() {
        return String.format("(%s)",playerChoices.keySet().stream().sorted().map(e->e.getName()+" -> "+playerChoices.get(e).name()).collect(Collectors.joining(", ")));
    }
}
