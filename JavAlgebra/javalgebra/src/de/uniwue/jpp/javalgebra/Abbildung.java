package de.uniwue.jpp.javalgebra;

import de.uniwue.jpp.javalgebra.mengen.EndlicheMenge;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Abbildung<T,S> {
    private Menge<T> definitionsmenge;private Menge<S> zielmenge;private Function<T,S> abbVorschrift;
    public Abbildung(Menge<T> definitionsmenge, Menge<S> zielmenge, Function<T,S> abbVorschrift) {
        if(zielmenge.getSize().isEmpty() || definitionsmenge.getSize().isEmpty())throw new IllegalArgumentException("Unendliche Menge");
        for(T element : definitionsmenge.getElements().toList()){
            if(!zielmenge.getElements().toList().contains(abbVorschrift.apply(element))){
                throw new IllegalArgumentException("diese Element existiert nicht");
            }
        }
        this.abbVorschrift= abbVorschrift;
        this.zielmenge= zielmenge;
        this.definitionsmenge= definitionsmenge;

    }

    public S apply(T t) {
        if(!definitionsmenge.getElements().toList().contains(t))throw new IllegalArgumentException("diese Element existiert nicht");
        return abbVorschrift.apply(t);
    }

    public Menge<S> getBildVon(Menge<T> m) {
        Predicate<T>predicate= t->definitionsmenge.getElements().toList().contains(t);
        if(!definitionsmenge.getElements().allMatch(predicate)){
            throw new IllegalArgumentException("keine Untermenge");
        }
        Set<S> set= new HashSet<>();
        for (T elm: m.getElements().toList()){
            set.add(abbVorschrift.apply(elm));
        }
        return  new EndlicheMenge<>(set);
    }

    public Menge<T> getUrbildVon(Menge<S> m) {
        Predicate<S>predicate= s->zielmenge.getElements().toList().contains(s);
        if(!m.getElements().allMatch(predicate))throw new IllegalArgumentException("element exist nicht");
        Set<T> set= new HashSet<>();
        for (T elm: definitionsmenge.getElements().toList()){
            S ziel= abbVorschrift.apply(elm);
            if(m.contains(ziel)){
                set.add(elm);
            }
        }
        return new EndlicheMenge<>(set);
    }

    public boolean isInjektiv() {
        for(S elem: zielmenge.getElements().toList()){
            int i=0;
            for(T elem2: definitionsmenge.getElements().toList()){
                if(abbVorschrift.apply(elem2).equals(elem)) i++;
            }
            if(i>1)return false;
        }
        return true;
    }

    public boolean isSurjektiv() {
        for(S elem: zielmenge.getElements().toList()){
            int i=0;
            for(T elem2: definitionsmenge.getElements().toList()){
                if(abbVorschrift.apply(elem2).equals(elem)) i++;
            }
            if(i<1)return false;
        }
        return true;
    }

    public boolean isBijektiv() {
        return isInjektiv() && isSurjektiv();
    }

    public Abbildung<S, T> getUmkehrabbildung() {
        if(!isBijektiv())throw new UnsupportedOperationException("");
        Function<S,T> function = new Function<S, T>() {
            @Override
            public T apply(S s) {
                for(T elem: definitionsmenge.getElements().toList()){
                    if(abbVorschrift.apply(elem).equals(s))return elem;
                }
                  return null;
            }
        };
        return new Abbildung<>(getBildVon(definitionsmenge),getUrbildVon(zielmenge),function);
    }
}
