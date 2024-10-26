package de.uniwue.jpp.javalgebra.mengen;

import de.uniwue.jpp.javalgebra.Menge;

import java.math.BigInteger;
import java.util.Optional;
import java.util.stream.Stream;

public class NatuerlicheZahlen implements Menge<BigInteger> {

    @Override
    public Stream<BigInteger> getElements() {
        Stream<BigInteger> stream= Stream.iterate(BigInteger.ONE,n ->n.add(BigInteger.ONE));
        return stream;
    }

    @Override
    public boolean contains(BigInteger element) {
        if(element.compareTo(BigInteger.ONE)<0)return false;
        return true;
    }

    @Override
    public Optional<Integer> getSize() {
        return Optional.empty();
    }
}
