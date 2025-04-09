package de.uniwue.jpp.enigma;

import java.io.*;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import de.uniwue.jpp.encoder.Encoder;
import de.uniwue.jpp.encoder.EncoderCreationException;
import de.uniwue.jpp.encoder.EncoderInputException;
import de.uniwue.jpp.encoder.FixedEncoder;
import de.uniwue.jpp.encoder.Reflector;
import de.uniwue.jpp.encoder.RotatingEncoder;

public class Enigma {
    private FixedEncoder first;


    public Enigma(FixedEncoder first) throws EnigmaCreationException {
        if (first == null) throw new EnigmaCreationException();
        this.first = first;

    }

    public Enigma(InputStream is) throws IOException, EnigmaCreationException, EncoderCreationException {
        if (is == null) throw new EnigmaCreationException();
        ArrayList<String> encoder = new ArrayList<>();
        int folger = 0;
        LinkedHashMap<String, HashMap<Character, Character>> data = new LinkedHashMap<>();
        Scanner scan = new Scanner(is);
        if (!scan.hasNext()) throw new EnigmaCreationException();
        String current = "";
        while (scan.hasNext()) {
            String s = "";
            if ((s = scan.nextLine()).contains("#")) {
                encoder.add(s);
                current = s + folger;
                data.put(current, new HashMap<>());
                folger++;


            } else if (!s.contains("->")) throw new EnigmaCreationException();
            else {
                String[] split = s.split("->");
                if (split.length != 2) throw new EnigmaCreationException();
                if (!split[0].matches("[a-z]") || !split[1].matches("[a-z]"))
                    throw new EnigmaCreationException();
                data.get(current).put(split[0].charAt(0), split[1].charAt(0));
            }

        }
        if (encoder.size() == 1) throw new EnigmaCreationException(); // überprüfen ,ob es nur ein Encoder gibt.
        for (int i = 0; i < encoder.size(); i++) {  // überprüfen ,ob es gleiche Benachbare Encoder gibt.
            if ((i + 1) < encoder.size()) {
                if (encoder.get(i).equals(encoder.get(i + 1))) {
                    throw new EnigmaCreationException();
                }
            }
        }
        if (encoder.stream().filter(e -> e.equals("# reflecting")).count() > 1)
            throw new EnigmaCreationException();
        if (!encoder.get(0).equals("# fixed")) throw new EnigmaCreationException();
        if (!encoder.get(encoder.size() - 1).equals("# reflecting")) throw new EnigmaCreationException();

        // überprüfen, ob Rotating korrekt Format hat.
        List<String> rotatingEncoderlist = encoder.stream().filter(e -> e.startsWith("# rotating")).toList();
        for (String s : rotatingEncoderlist) {
            String[] split = s.split(" ");
            int rotationValue = Integer.parseInt(split[2]);
            if (split.length != 3 || rotationValue < 0 || rotationValue > 25) throw new EnigmaCreationException();
        }

        if(data.keySet().stream().anyMatch(e->data.get(e).size()!=26)) throw new EnigmaCreationException();
        if(data.keySet().stream().anyMatch(e->new HashSet<>(data.get(e).values()).size()!= new ArrayList<>(data.get(e).values()).size()))
            throw new EnigmaCreationException();

        // erstellung von Encoder mit Ihre Nachfolger.
        List<String> keys = new ArrayList<>(data.keySet());
        Collections.reverse(keys);
        Encoder next = null;
        for (String s : keys) {
            if (s.startsWith("# reflecting")) {
                next = new Reflector(data.get(s));
            } else if (s.startsWith("# fixed")) {
                next = new FixedEncoder(next, data.get(s));
            } else if (s.startsWith("# rotating")) {
                String wert = s.split(" ")[2];
                int rotationValue = Integer.parseInt(wert.substring(0, wert.length() - 1));
                next = new RotatingEncoder(next, data.get(s), rotationValue);

            }
        }
        if (next instanceof FixedEncoder) {
            this.first = (FixedEncoder) next;
        }

    }

    public String encrypt(InputStream is) throws IOException, EnigmaEncryptionException {
        try {

            if (is == null) throw new EnigmaEncryptionException();

            Scanner scan = new Scanner(is);
            if (!scan.hasNext()) throw new EnigmaEncryptionException();
            String text = "";
            Encoder next = first.getDelegate();
            boolean wert=false;
            int zahl=0;
            while (scan.hasNext()) {
                String s = scan.nextLine();
                if (!(s.equals(s.toLowerCase()))) {
                    throw new EnigmaEncryptionException();
                }
                for (Character c : s.toCharArray()) {
                    if (c == ' ') {
                        text += c;
                    } else {
                        text += first.encode(c);
                        first.rotate(true);

                    }
                }
                text += "\n";
            }
            return text.substring(0, text.length() - 1);
        } catch (Exception e) {
            throw new EnigmaEncryptionException();
        }
    }

    public Encoder getFirst() {
        return first;
    }

    public String toString() {
        String text = "# fixed\n";
        for (Character c : first.getMap().keySet()) {
            text = text + c + "->" + first.getMap().get(c) + "\n";
        }
        Encoder encoder = first.getDelegate();
        while (!(encoder instanceof Reflector)) {
            if (encoder instanceof RotatingEncoder) {
                text += "# rotating\n";
                for (Character c : ((RotatingEncoder) encoder).getMap().keySet()) {
                    text = text + c + "->" + ((RotatingEncoder) encoder).getMap().get(c) + "\n";
                }
                encoder = ((RotatingEncoder) encoder).getDelegate();
            } else if (encoder instanceof FixedEncoder) {
                text = "# fixed\n";
                for (Character c : ((FixedEncoder) encoder).getMap().keySet()) {
                    text = text + c + "->" + ((FixedEncoder) encoder).getMap().get(c) + "\n";
                }
                encoder = ((FixedEncoder) encoder).getDelegate();
            }
        }
        text += "# reflecting\n";
        for (Character c : ((Reflector) encoder).getMap().keySet()) {
            text = text + c + "->" + ((Reflector) encoder).getMap().get(c) + "\n";
        }

        return text.substring(0, text.length() - 1);
    }


    public static void main(String[] args) throws IOException, EnigmaCreationException, EnigmaEncryptionException, EncoderCreationException, EncoderInputException {
        Enigma enigma = new Enigma(new FileInputStream("C:\\Users\\fabrice\\Desktop\\text2.Enigma.txt"));

        System.out.println(enigma.encrypt(new FileInputStream("C:\\Users\\fabrice\\Desktop\\Enigma.txt")));
       //String text2="hel2o wel!";
       //System.out.println(text2.matches("[a-z ]+"));


    }
}
