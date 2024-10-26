package de.uniwue.jpp.javalgebra;

import de.uniwue.jpp.javalgebra.mengen.RestklassenMenge;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class Relation<T> implements Menge<Tupel<T>> {
    Set<Tupel<T>> tupelSet;
    private Menge<T> menge;
    private BiFunction<T,T,Boolean> isInRelation;
    public Relation(Menge<T> menge, BiFunction<T,T,Boolean> isInRelation) {
        if(menge.getSize().isEmpty()) throw new IllegalArgumentException("unendliche Menge");
        tupelSet= new HashSet<>();
        for (T element1: menge.getElements().toList()){
            for (T element2: menge.getElements().toList()){
                if(isInRelation.apply(element1,element2)){
                    tupelSet.add( new Tupel<>(element1,element2));
                }
            }
        }
        this.menge= menge;
       this.isInRelation= isInRelation;
    }

    @Override
    public Stream<Tupel<T>> getElements() {
        return tupelSet.stream();
    }

    @Override
    public boolean contains(Tupel<T> element) {
        return tupelSet.contains(element);
    }

    public boolean isReflexiv() {
        int count= (int)getElements().filter(e->e.getFirst().equals(e.getSecond())).count();
        if(menge.getSize().isPresent()){
            if(menge.getSize().get()== count)return true;
        }
        return false;
    }

    public boolean isIrreflexiv() {
        return getElements().noneMatch(e->e.getFirst().equals(e.getSecond()));
    }

    public boolean isSymmetrisch() {
        for (Tupel<T> tupel: tupelSet){
            if(!tupel.getFirst().equals(tupel.getSecond()) && !tupelSet.contains(new Tupel<>(tupel.getSecond(), tupel.getFirst())))
                return false;
        }
        return true;
    }

    public boolean isAsymmetrisch() {
        if(tupelSet.isEmpty())return true;
        if(getElements().anyMatch(e->e.getFirst().equals(e.getSecond())))return false;
        for (Tupel<T> tupel: tupelSet){
            if(tupelSet.contains(new Tupel<>(tupel.getSecond(), tupel.getFirst())))
                return false;
        }
        return true;
    }

    public boolean isAntisymmetrisch() {
        if(menge.getSize().isEmpty() && Math.pow(menge.getSize().get(),2)==tupelSet.size())return false;
        if(getElements().allMatch(e->e.getFirst().equals(e.getSecond())))return true;
        for (Tupel<T> tupel: tupelSet){
            if(!tupel.getFirst().equals(tupel.getSecond()) && tupelSet.contains(new Tupel<>(tupel.getSecond(), tupel.getFirst())))
                return false;
        }
        return true;
    }

    public boolean isTransitiv() {
        if(getElements().allMatch(e->e.getFirst().equals(e.getSecond())))return true;
        for (Tupel<T> tupel: tupelSet) {
            List<Tupel<T>> relation= getElements().filter(e->!e.equals(tupel) && (e.getFirst().equals(tupel.getSecond()) || e.getSecond().equals(tupel.getSecond()))).toList();
            for(Tupel<T> tupel2: relation){
                if(!tupelSet.contains(new Tupel<>(tupel.getFirst(),tupel2.getSecond()))){
                    return false;
                }
            }
        }
       return true;
    }

    public boolean isTotal() {
        if(getElements().allMatch(e->e.getFirst().equals(e.getSecond())))return false;
        for(T element: menge.getElements().toList()){
            for(T element1: menge.getElements().toList()){
                if(!isInRelation.apply(element,element1) && !isInRelation.apply(element1,element)){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isAequivalenzrelation() {
        return isReflexiv() && isSymmetrisch() && isTransitiv();
    }

    public Set<Set<T>> getAequivalenzklassen() {
        if(!isAequivalenzrelation())throw new UnsupportedOperationException("keine Aequivalenzrelation");
        Set<Set<T>> setSet = new HashSet<>();
        for(T element: menge.getElements().toList()){
            Set<T> set= new HashSet<>();
            for (T element1: menge.getElements().toList()){
                if(isInRelation.apply(element,element1)){
                    set.add(element1);
                }
            }
            setSet.add(set);
        }
        return setSet;
    }

    public boolean isTotalordnung() {
        return isReflexiv() &&  isAntisymmetrisch() && isTransitiv() && isTotal();
    }

    public List<T> getElementsInOrder() {
        if(!isTotalordnung())throw new UnsupportedOperationException("ist nicht Totalordnung");
        List<T> list= new ArrayList<>(menge.getElements().toList());
        list.sort(new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                if(isInRelation.apply(o1,o2))return -1;
                else if(isInRelation.apply(o2,o1))return 1;
                else return 0;
            }
        });
        return list;
    }

    public static void main(String[] args) {
        BiFunction<Integer,Integer, Boolean> b= (t1,t2)->t1<=t2;
       Relation<Integer> r= new Relation<>(new RestklassenMenge(3),b);
       // System.out.println(new RestklassenMenge(3).getSize());
        System.out.println(fakultaet(5));

    }
    public static int fakultaet(int n){
        int fak= 1;
        while(n>1){
            fak*=n;
            n--;
        }
        return fak;
    }
}
