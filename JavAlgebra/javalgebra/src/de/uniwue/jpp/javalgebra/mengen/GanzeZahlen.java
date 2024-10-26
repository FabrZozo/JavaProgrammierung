package de.uniwue.jpp.javalgebra.mengen;

import de.uniwue.jpp.javalgebra.Menge;

import java.math.BigInteger;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GanzeZahlen implements Menge<BigInteger> {

    @Override
    public Stream<BigInteger> getElements() {
        Stream<BigInteger> stream=Stream.iterate(BigInteger.ZERO,n->{
               if(n.signum()==0)return n.add(BigInteger.ONE);
               else if(n.signum()>0) return  n.negate();
               return n.negate().add(BigInteger.ONE);
        });
        return stream;
    }

    @Override
    public boolean contains(BigInteger element) {
        return element.compareTo(BigInteger.ZERO)<=0 || element.compareTo(BigInteger.ZERO)>0;
    }

    @Override
    public Optional<Integer> getSize() {
        return Optional.empty();
    }

    public static void main(String[] args) {
        GanzeZahlen ganzeZahlen= new GanzeZahlen();
        System.out.println(ganzeZahlen.getElements().limit(10).collect(Collectors.toList()));
    }
}
