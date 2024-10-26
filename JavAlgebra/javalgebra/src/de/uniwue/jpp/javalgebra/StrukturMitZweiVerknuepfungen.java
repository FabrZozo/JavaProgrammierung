package de.uniwue.jpp.javalgebra;

import de.uniwue.jpp.javalgebra.mengen.EndlicheMenge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StrukturMitZweiVerknuepfungen<T> {
    private Menge<T> menge;
    private Abbildung<Tupel<T>, T> plus;
    private Abbildung<Tupel<T>, T> mal;

    public StrukturMitZweiVerknuepfungen(Menge<T> menge, Abbildung<Tupel<T>, T> plus, Abbildung<Tupel<T>, T> mal) {
        if (menge.getSize().isEmpty()) throw new IllegalArgumentException("unendliche Menge");
        List<T> listMenge = menge.getElements().toList();
        for (T element : listMenge) {
            if (menge.getElements().noneMatch(e -> listMenge.contains(plus.apply(new Tupel<>(e, element)))))
                throw new IllegalArgumentException("");
            if (menge.getElements().noneMatch(e -> listMenge.contains(mal.apply(new Tupel<>(e, element)))))
                throw new IllegalArgumentException("");
        }
        this.mal = mal;
        this.plus = plus;
        this.menge = menge;
    }

    public T applyPlus(T t1, T t2) {
        if (!menge.contains(t1) && !menge.contains(t2)) {
            throw new IllegalArgumentException(" ");
        }
        return plus.apply(new Tupel<>(t1, t2));
    }

    public T applyMal(T t1, T t2) {
        if (!menge.contains(t1) && !menge.contains(t2)) {
            throw new IllegalArgumentException(" ");
        }
        return mal.apply(new Tupel<>(t1, t2));
    }

    public boolean isDistributiv() {
        for (T element : menge.getElements().toList()) {
            for (T element1 : menge.getElements().toList()) {
                for (T element2 : menge.getElements().toList()) {
                    if (!mal.apply(new Tupel<>(element, plus.apply(new Tupel<>(element1, element2)))).equals(
                            plus.apply(new Tupel<>(mal.apply(new Tupel<>(element, element1)), mal.apply(new Tupel<>(element, element2)))))) {
                        return false;
                    }
                    if (!mal.apply(new Tupel<>(plus.apply(new Tupel<>(element, element1)), element2)).equals(
                            plus.apply(new Tupel<>(mal.apply(new Tupel<>(element, element2)), mal.apply(new Tupel<>(element1, element2)))))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isRing() {
        StrukturMitEinerVerknuepfung<T> menge3 = new StrukturMitEinerVerknuepfung<>(menge, plus);
        StrukturMitEinerVerknuepfung<T> menge4 = new StrukturMitEinerVerknuepfung<>(menge, mal);
        if (menge3.isAbelscheGruppe() && menge4.isHalbgruppe() && this.isDistributiv())
            return true;
        return false;
    }

    public T getNull() {
        if (!isRing()) throw new UnsupportedOperationException("");
        StrukturMitEinerVerknuepfung<T> menge3 = new StrukturMitEinerVerknuepfung<>(menge, plus);
        return menge3.getNeutralesElement();
    }

    public boolean isKoerper() {
        if (!isRing()) return false;
        Set<T> set= menge.getElements().filter(e->!e.equals(getNull())).collect(Collectors.toSet());

            StrukturMitEinerVerknuepfung<T> menge4 = new StrukturMitEinerVerknuepfung<>(new EndlicheMenge<>(set), mal);
            if(menge4.isAbelscheGruppe())return true;
            return false;


    }

    public T getEins() {
        if(!isKoerper())throw new UnsupportedOperationException("");
        Set<T> set= menge.getElements().filter(e->!e.equals(getNull())).collect(Collectors.toSet());
        StrukturMitEinerVerknuepfung<T> menge3 = new StrukturMitEinerVerknuepfung<>(new EndlicheMenge<>(set), mal);
        return menge3.getNeutralesElement();
    }
}
