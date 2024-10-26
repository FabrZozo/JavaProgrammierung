package jpp.gametheory.generic;

import java.util.*;
import java.util.stream.Collectors;

public class Game<C extends IChoice> {
    private Set<IPlayer<C>> players;
    private IReward<C> reward;
    List<IGameRound<C>> previousRounds;

    public Game(Set<IPlayer<C>> players, IReward<C> reward) {
        if (players == null || reward == null) throw new NullPointerException();
        if (players.isEmpty()) throw new IllegalArgumentException();
        this.players = players;
        this.reward = reward;
        previousRounds = new ArrayList<>();
    }

    public Set<IPlayer<C>> getPlayers() {
        return players;
    }

    public IGameRound<C> playRound() {
        Map<IPlayer<C>, C> map = new HashMap<>();
        for (IPlayer<C> p : players) {
            map.put(p, p.getChoice(previousRounds));
        }
        IGameRound<C> round = new GameRound<>(map);
        previousRounds.add(round);
        return round;
    }

    public void playNRounds(int n) {
        if (n < 1) throw new IllegalArgumentException();
        for (int i = 0; i < n; i++) {
            playRound();
        }
    }

    public Optional<IGameRound<C>> undoRound() {
        if (previousRounds.isEmpty()) return Optional.empty();
        IGameRound<C> round = previousRounds.removeLast();
        return Optional.of(round);
    }

    public void undoNRounds(int n) {

        if (n < 1) throw new IllegalArgumentException();
        if (previousRounds.size() < n) {
            previousRounds.clear();
        } else {
            for (int i = 0; i < n; i++) {
                previousRounds.removeLast();
            }
        }

    }

    public List<IGameRound<C>> getPlayedRounds() {
        return Collections.unmodifiableList(previousRounds);
    }

    public int getPlayerProfit(IPlayer<C> player) {
        if(player== null)throw new NullPointerException();
        if(!players.contains(player))throw new IllegalArgumentException();
        return previousRounds.stream().mapToInt(e-> reward.getReward(player,e)).sum();
    }

    public Optional<IPlayer<C>> getBestPlayer() {
        IPlayer<C> best=new ArrayList<>(players).get(0);
        int max= getPlayerProfit(best);
        for (IPlayer<C> player: players){
            if(!player.equals(best) && max<getPlayerProfit(player)){
                max= getPlayerProfit(player);
                best= player;
            }
        }
        int finalMax = max;
        if(players.stream().filter(e->getPlayerProfit(e)== finalMax).count()>1)return Optional.empty();
        else return Optional.of(best);
    }

    public String toString() {

         StringBuilder builder= new StringBuilder();
         Map<IPlayer<C>,Integer> iPlayerIntegerMap = players.stream().collect(Collectors.toMap(e->e, this::getPlayerProfit));
         List<Map.Entry<IPlayer<C>,Integer>> sortierteList= iPlayerIntegerMap.entrySet().stream().sorted(new Comparator<Map.Entry<IPlayer<C>,Integer>>() {
             @Override
             public int compare(Map.Entry<IPlayer<C>,Integer> o1, Map.Entry<IPlayer<C>,Integer> o2) {
                 int com = o2.getValue().compareTo(o1.getValue());
                 if(com!=0)return com;
                 else return o1.getKey().getName().compareTo(o2.getKey().getName());
             }
         }).toList();
         builder.append("Spiel nach "+previousRounds.size()+" Runden:\n");
         builder.append("Profit : Spieler\n");
         for(int i = 0; i<sortierteList.size() ; i++){
           builder.append(sortierteList.get(i).getValue()+" : "+ sortierteList.get(i).getKey().toString()+"\n");
         }
         return builder.substring(0,builder.toString().length()-1);
    }

    public static void main(String[] args) {
        List<String> integerList = new ArrayList<>();
        integerList.add("1");
        integerList.add("3");
        integerList.add("2");
        String a = integerList.get(0);
        integerList.remove(0);
        System.out.println(integerList);
        System.out.println(a);
    }
}
