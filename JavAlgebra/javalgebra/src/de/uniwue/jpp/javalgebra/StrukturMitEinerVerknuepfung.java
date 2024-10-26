package de.uniwue.jpp.javalgebra;

import java.util.List;

public class StrukturMitEinerVerknuepfung<T> {
    private Menge<T> menge;
    private Abbildung<Tupel<T>, T> verknuepfung;

    public StrukturMitEinerVerknuepfung(Menge<T> menge, Abbildung<Tupel<T>, T> verknuepfung) {
        if (menge.getSize().isEmpty()) throw new IllegalArgumentException("unendliche Menge");
        List<T> listMenge = menge.getElements().toList();
        for (T element : listMenge) {
            if (menge.getElements().noneMatch(e -> listMenge.contains(verknuepfung.apply(new Tupel<>(e, element)))))
                throw new IllegalArgumentException("");
        }
        this.verknuepfung = verknuepfung;
        this.menge = menge;

    }

    public T apply(T t1, T t2) {
        if (!menge.contains(t1) && !menge.contains(t2)) {
            throw new IllegalArgumentException(" ");
        }
        return verknuepfung.apply(new Tupel<>(t1, t2));
    }

    public boolean isHalbgruppe() {
        for (T element : menge.getElements().toList()) {
            for (T element1 : menge.getElements().toList()) {
                for (T element2 : menge.getElements().toList()) {
                    if (!verknuepfung.apply(new Tupel<>(verknuepfung.apply(new Tupel<>(element, element1)), element2)).equals(
                            verknuepfung.apply(new Tupel<>(element, verknuepfung.apply(new Tupel<>(element1, element2)))))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isMonoid() {
        if (isHalbgruppe()) {
            for (T element : menge.getElements().toList()) {
                int i = 0;
                for (T element1 : menge.getElements().toList()) {
                    if (verknuepfung.apply(new Tupel<>(element, element1)).equals(element1) && verknuepfung.apply(new Tupel<>(element1, element)).equals(element1)) {
                        i++;
                    }
                }
                if (i == menge.getElements().toList().size()) return true;
            }
        } else {
            return false;
        }
        return false;
    }

    public T getNeutralesElement() {
        if (!isMonoid()) throw new UnsupportedOperationException(" ");
        for (T element : menge.getElements().toList()) {
            int i = 0;
            for (T element1 : menge.getElements().toList()) {
                if (verknuepfung.apply(new Tupel<>(element, element1)).equals(element1) && verknuepfung.apply(new Tupel<>(element1, element)).equals(element1)) {
                    i++;
                }
            }
            if (i == menge.getElements().toList().size()) return element;
        }
        return null;
    }

    public boolean isGruppe() {
        if (!isMonoid()) return false;
        for (T element1 : menge.getElements().toList()) {
            boolean wahr= false;
            for (T element2 : menge.getElements().toList()) {
                    if (verknuepfung.apply(new Tupel<>(element1, element2)).equals(getNeutralesElement()) &&
                            verknuepfung.apply(new Tupel<>(element2, element1)).equals(getNeutralesElement()))
                        wahr=true;
            }
            if(!wahr)return false;
        }

        return true;
    }

    public boolean isKommutativ() {
        for (T element1 : menge.getElements().toList()) {
            for (T element2 : menge.getElements().toList()) {
                if (!verknuepfung.apply(new Tupel<>(element1, element2)).equals(verknuepfung.apply(new Tupel<>(element2, element1))))
                    return false;
            }
        }
        return true;
    }

    public boolean isAbelscheGruppe() {
        return isKommutativ() && isGruppe();
    }
}
