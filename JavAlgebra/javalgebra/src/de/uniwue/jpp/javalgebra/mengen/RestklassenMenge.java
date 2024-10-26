package de.uniwue.jpp.javalgebra.mengen;

import de.uniwue.jpp.javalgebra.Menge;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class RestklassenMenge implements Menge<Integer> {
    private int mod;
    private Set<Integer> integerSet;
    public RestklassenMenge(int mod) {
        if(mod<=0) throw new IllegalArgumentException("zahl kleiner als 0");
        integerSet= new HashSet<>();
        integerSet.add(0);
        for (int i = 1; i < mod; i++) {
             if(i%mod!=0) integerSet.add(i);
        }
    }

    @Override
    public Stream<Integer> getElements() {
        return integerSet.stream();
    }

    @Override
    public boolean contains(Integer element) {
        return  getElements().anyMatch(e->e.equals(element));
    }

    @Override
    public Optional<Integer> getSize() {
        return Optional.of(integerSet.size());
    }

    public static void main(String[] args) {
        RestklassenMenge restklassenMenge= new RestklassenMenge(3);
        System.out.println(restklassenMenge.asString());
    }
}
