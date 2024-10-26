package de.uniwue.jpp.javalgebra.mengen;

import de.uniwue.jpp.javalgebra.Menge;
import de.uniwue.jpp.javalgebra.Tupel;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TupelMenge<T> implements Menge<Tupel<T>> {
    Set<Tupel<T>> tupelSet;
    public TupelMenge(Menge<T> menge) {
        if(menge.getSize().isEmpty())throw new IllegalArgumentException("unendliche Menge");
        tupelSet= new HashSet<>();
        for(T element1:menge.getElements().toList()){
            for(T element2: menge.getElements().toList()){
                tupelSet.add( new Tupel<>(element1,element2));
            }
        }
    }

    @Override
    public Stream<Tupel<T>> getElements() {
        return tupelSet.stream();
    }

    @Override
    public boolean contains(Tupel<T> element) {
        return tupelSet.contains(element);
    }

    @Override
    public Optional<Integer> getSize() {
        return Optional.of(tupelSet.size());
    }
}
