package de.uniwue.jpp.encoder;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class RotatingEncoder extends AbstractChainableEncoder {
    private HashMap<Character, Character> map;
    private int rotations;
    char initial;
    char last;
    int zaehler;

    public RotatingEncoder(Encoder delegate) throws EncoderCreationException {
        super(delegate);
        HashMap<Character, Character> map2 = new HashMap<>();
        for (Character e : listOfCharater()) {
            map2.put(e, e);
        }
        this.map = map2;
        zaehler = 0;

    }

    public RotatingEncoder(Encoder delegate, HashMap<Character, Character> map, int rotations) throws EncoderCreationException {
        super(delegate);
        if (map == null) throw new EncoderCreationException();
        for (Character e : map.keySet()) {
            if (!listOfCharater().contains(e) || !listOfCharater().contains(map.get(e)))
                throw new EncoderCreationException();
        }
        if (map.keySet().size() != 26) throw new EncoderCreationException();
        if (new HashSet<>(map.values()).size() != new ArrayList<>(map.values()).size())
            throw new EncoderCreationException();
        if (rotations < 0 || rotations > 25) throw new EncoderCreationException();
        this.map = new HashMap<>(map);
        initial = 'a';
        last = 'z';
        zaehler = 0;
        this.rotations = rotations;
    }

    @Override
    public char encode(char c) throws EncoderInputException {
        if (!(c >= 'a' && c <= 'z')) throw new EncoderInputException();
        char charater = map.get(c);
        if (getDelegate() == null) {
            return charater;
        } else {
            charater = getDelegate().encode(charater);
            for (Character c1 : map.keySet()) {
                if (charater == map.get(c1)) return c1;
            }
        }
        return charater;
    }

    @Override
    public void rotate(boolean carry) {

        if (carry) {
            char wert= map.get('a');
            for (char i = 'a'; i <='z' ; i++)
             {
                if ('z' ==i) {
                    map.put('z',wert);
                } else
                    map.put(i, (char) (map.get((char)(i+1))));
            }
            zaehler++;
        }
        if (zaehler == 26) {
            getDelegate().rotate(true);
            zaehler = 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RotatingEncoder that = (RotatingEncoder) o;
        return Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(map);
    }

    @Override
    public String toString() {
        Map<Character, Character> sortierungCharater = new TreeMap<>(map);
        return String.format("%s", sortierungCharater.keySet().stream().map(e -> e + "->" + sortierungCharater.get(e)).collect(Collectors.joining("\n")));
    }

    public List<Character> listOfCharater() {
        List<Character> alphabet = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            alphabet.add(c);
        }
        return alphabet;
    }
    public HashMap<Character,Character> getMap(){
        return map;
    }
    public int getRotations(){return rotations;}

    public static void main(String[] args) throws EncoderCreationException {

        HashMap<Character, Character> map1 = new HashMap<>();
        map1.put('a', 'y');
        map1.put('b', 'z');
        map1.put('c', 'x');
        map1.put('d', 'w');
        map1.put('e', 'v');
        map1.put('f', 'u');
        map1.put('g', 't');
        map1.put('h', 's');
        map1.put('i', 'r');
        map1.put('j', 'q');
        map1.put('k', 'p');
        map1.put('l', 'o');
        map1.put('m', 'n');
        map1.put('n', 'm');
        //
        map1.put('o', 'l');
        map1.put('p', 'k');
        map1.put('q', 'j');
        map1.put('r', 'i');
        map1.put('s', 'h');
        map1.put('t', 'g');
        map1.put('u', 'f');
        map1.put('v', 'e');
        map1.put('w', 'd');
        map1.put('x', 'c');
        map1.put('y', 'a');
        map1.put('z', 'b');
        Reflector reflector = new Reflector(map1);
        RotatingEncoder r = new RotatingEncoder(reflector, map1, 1);
        r.rotate(true);


        System.out.println(r.toString());

    }
}