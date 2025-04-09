package de.uniwue.jpp.encoder;

import java.util.*;
import java.util.stream.Collectors;

public class FixedEncoder extends AbstractChainableEncoder {
    private HashMap<Character, Character> map;


    public FixedEncoder(Encoder delegate, HashMap<Character, Character> map) throws EncoderCreationException {
        super(delegate);

        if (map == null) throw new EncoderCreationException();
        for(Character e: map.keySet()){
            if(!listOfCharater().contains(e)|| !listOfCharater().contains(map.get(e)))
                throw new EncoderCreationException();
        }
        if (map.keySet().size() != 26) throw new EncoderCreationException();
        if (new HashSet<>(map.values()).size() != map.values().size()) throw new EncoderCreationException();
        this.map = map;

    }

    @Override
    public char encode(char c) throws EncoderInputException {
        if (!(c >= 'a' && c <= 'z')) throw new EncoderInputException();
        char charater = map.get(c);
        if(getDelegate()== null){
            return charater;
        }else{
            charater= getDelegate().encode(charater);
            for(Character c1: map.keySet()){
                if(charater==map.get(c1))return c1;
            }
        }
        return charater;
    }
    public HashMap<Character,Character> getMap(){
        return map;
    }

    @Override
    public void rotate(boolean carry) {
        this.getDelegate().rotate(carry);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixedEncoder that = (FixedEncoder) o;
        return Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(map);
    }

    @Override
    public String toString() {
        Map<Character,Character> sortierungCharater= new TreeMap<>(map);
        return String.format("%s", sortierungCharater.keySet().stream().map(e->e+"->"+sortierungCharater.get(e)).collect(Collectors.joining("\n")));
    }


    public List<Character> listOfCharater() {
        List<Character> alphabet = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            alphabet.add(c);
        }
        return alphabet;
    }



    public static void main(String[] args) throws EncoderCreationException, EncoderInputException {
        HashMap<Character,Character> mapRotating= new HashMap<>();
        HashMap<Character,Character> mapRefletor= new HashMap<>();
        HashMap<Character,Character> mapfixed= new HashMap<>();
        mapfixed.put('a','b');mapfixed.put('b','c');mapfixed.put('c','d');mapfixed.put('d','e');
        mapfixed.put('e','f');mapfixed.put('f','g');mapfixed.put('g','h');mapfixed.put('h','i');mapfixed.put('i','j');
        mapfixed.put('j','k');mapfixed.put('k','l');mapfixed.put('l','m');mapfixed.put('m','n');
        mapfixed.put('n','o');mapfixed.put('o','p');mapfixed.put('p','q');mapfixed.put('q','r');
        mapfixed.put('r','s');mapfixed.put('s','t');mapfixed.put('t','u');mapfixed.put('u','v');
        mapfixed.put('v','w');mapfixed.put('w','x');mapfixed.put('x','y');mapfixed.put('y','z');mapfixed.put('z','a');
        //
        mapRotating.put('a','a');mapRotating.put('b','b');mapRotating.put('c','c');mapRotating.put('d','d');mapRotating.put('e','e');
        mapRotating.put('f','f');mapRotating.put('g','g');mapRotating.put('h','h');mapRotating.put('i','i');
        mapRotating.put('j','j');mapRotating.put('k','k');mapRotating.put('l','l');mapRotating.put('m','m');
        mapRotating.put('n','n');mapRotating.put('o','o');mapRotating.put('p','p');mapRotating.put('q','q');
        mapRotating.put('r','r');mapRotating.put('s','s');mapRotating.put('t','t');mapRotating.put('u','u');
        mapRotating.put('v','v');mapRotating.put('w','w');mapRotating.put('x','x');mapRotating.put('y','y');
        mapRotating.put('z','z');
        //
        mapRefletor.put('a','z');mapRefletor.put('b','y');mapRefletor.put('c','x');
        mapRefletor.put('d','w');mapRefletor.put('e','v');mapRefletor.put('f','u');
        mapRefletor.put('g','t');mapRefletor.put('h','s');mapRefletor.put('i','r');
        mapRefletor.put('j','q');mapRefletor.put('k','p');mapRefletor.put('l','o');
        mapRefletor.put('m','n');mapRefletor.put('n','m');mapRefletor.put('o','l');
        mapRefletor.put('p','k');mapRefletor.put('q','j');mapRefletor.put('r','i');
        mapRefletor.put('s','h');mapRefletor.put('t','g');mapRefletor.put('u','f');
        mapRefletor.put('v','e');mapRefletor.put('w','d');mapRefletor.put('x','c');
        mapRefletor.put('y','b');mapRefletor.put('z','a');

        Reflector reflector= new Reflector(mapRefletor);
        RotatingEncoder rotatingEncoder= new RotatingEncoder(reflector,mapRotating,1);
        FixedEncoder fixedEncoder1= new FixedEncoder(rotatingEncoder,mapfixed);
        System.out.println(fixedEncoder1.encode('a'));

    }
}