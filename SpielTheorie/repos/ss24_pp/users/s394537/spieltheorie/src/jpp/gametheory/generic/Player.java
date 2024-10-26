package jpp.gametheory.generic;

import java.util.List;
import java.util.Objects;

public class Player<C extends IChoice> implements IPlayer<C> {
    private String name;private IStrategy<C> strategy;
    public Player(String name, IStrategy<C> strategy) {
        if(name== null || strategy== null )throw new NullPointerException();
        this.name= name;
        this.strategy=strategy;
    }

    @Override
    public String getName() {
        return  name;
    }

    @Override
    public IStrategy<C> getStrategy() {
        return strategy;
    }

    @Override
    public C getChoice(List<IGameRound<C>> previousRounds) {
        if(previousRounds==null)throw new NullPointerException();
        return strategy.getChoice(this,previousRounds);
    }

    @Override
    public int compareTo(IPlayer<C> o) {
        return  this.name.compareTo(o.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player<?> player = (Player<?>) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    // "<Spielername>(<Strategiename>)"
    @Override
    public String toString() {
        return name+"("+strategy.name()+")";
    }
}
