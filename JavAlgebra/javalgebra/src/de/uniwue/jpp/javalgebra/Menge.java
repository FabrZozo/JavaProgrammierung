package de.uniwue.jpp.javalgebra;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Menge<T> {

    Stream<T> getElements();

    default boolean contains(T element) {
        if (getElements().anyMatch(e -> e.equals(element))) return true;
        else return false;
    }

    default Optional<Integer> getSize() {
        return Optional.of((int) getElements().count());
    }

    default boolean isEmpty() {
        if (getSize().isPresent()) return getSize().get() == 0;
        return false;
    }

    default String asString(int maxDisplay) {
        if (maxDisplay <= 0) throw new IllegalArgumentException("maxDisplay ist 0 oder kleiner als 0");
        String s = "";
        if (isEmpty()) return "[]";
        if (getSize().isPresent()) {
            if (getSize().get() <= maxDisplay) {
                s = String.format("[%s]", getElements().map(e -> e + "").collect(Collectors.joining(", ")));
            }else{
                s += "[";
                s += getElements().limit(maxDisplay).map(e -> e + "").collect(Collectors.joining(", "));
                s += ", ...]";
            }
        }else {
                s += "[";
                s += getElements().limit(maxDisplay).map(e -> e + "").collect(Collectors.joining(", "));
                s += ", ...]";
        }

        return s;
    }

    default String asString() {
        if (getSize().isEmpty()) return asString(10);
        return String.format("[%s]", getElements().map(e -> e + "").collect(Collectors.joining(", ")));
    }


}
