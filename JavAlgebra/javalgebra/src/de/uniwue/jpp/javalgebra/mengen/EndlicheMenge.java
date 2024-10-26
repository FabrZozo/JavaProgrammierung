package de.uniwue.jpp.javalgebra.mengen;

import de.uniwue.jpp.javalgebra.Menge;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EndlicheMenge<T> implements Menge<T> {
    private Set<T> objects;
    public EndlicheMenge(Set<T> objects) {
        this.objects=objects;
    }

    @Override
    public Stream<T> getElements() {
        return objects.stream();

    }

    public EndlicheMenge<T> createUntermenge(Predicate<T> filter) {
        Set<T> untermenge= objects.stream().filter(filter).collect(Collectors.toSet());
        return new EndlicheMenge<>(untermenge);
    }
}
