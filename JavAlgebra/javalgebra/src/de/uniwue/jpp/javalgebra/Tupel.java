package de.uniwue.jpp.javalgebra;

import java.util.Objects;

public class Tupel<T> {
    private T first;private T second;
    public Tupel(T first, T second) {
        this.first= first;
        this.second= second;
    }

    public T getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tupel<?> tupel = (Tupel<?>) o;
        return Objects.equals(first, tupel.first) && Objects.equals(second, tupel.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {

        return "("+first.toString()+", "+second.toString()+")";
    }

    public static void main(String[] args) {
        System.out.println(new Tupel<Integer>(4,3).toString());
    }
}
