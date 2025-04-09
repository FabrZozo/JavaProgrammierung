package de.uniwue.jpp.encoder;

import java.util.*;
import java.util.stream.Collectors;

public class Reflector implements Encoder {
	private HashMap<Character, Character> map;
	public Reflector(HashMap<Character, Character> map) throws EncoderCreationException {
		if(map== null)throw new EncoderCreationException();
		for(Character e: map.keySet()){
			if(!listOfCharater().contains(e)|| !listOfCharater().contains(map.get(e)))
				throw new EncoderCreationException();
		}
		if (map.keySet().size() != 26) throw new EncoderCreationException();
		if (new HashSet<>(map.values()).size() != new ArrayList<>(map.values()).size()) throw new EncoderCreationException();
		for(Character e: map.keySet()){
			char wert= map.get(e);
			if(!map.get(wert).equals(e))throw new EncoderCreationException();
		}
		this.map= map;
	}

	@Override
	public char encode(char c) throws EncoderInputException {
		   if (!(c >= 'a' && c <= 'z')) throw new EncoderInputException();
           return map.get(c);
	}

	@Override
	public void rotate(boolean carry) {
		System.out.println(carry);
	}
	public HashMap<Character,Character> getMap(){
		return map;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Reflector reflector = (Reflector) o;
		return Objects.equals(map, reflector.map);
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
		HashMap<Character,Character> map1= new HashMap<>();
        String text="a->b\n";
		String [] split= text.split("->");


	}
}