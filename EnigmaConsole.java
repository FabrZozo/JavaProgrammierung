package de.uniwue.jpp.enigma;

import de.uniwue.jpp.encoder.EncoderCreationException;
import de.uniwue.jpp.encoder.FixedEncoder;
import de.uniwue.jpp.encoder.Reflector;
import de.uniwue.jpp.encoder.RotatingEncoder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class EnigmaConsole {

    public static void main(String[] args) {
        EnigmaConsole console = new EnigmaConsole();
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        console.run(inputStream, outputStream);
    }

    public void run(InputStream is, OutputStream os) {
        try {
            os.write("Enigma wird gestartet.\n".getBytes());
            Scanner scan1 = new Scanner(is);
            boolean schrittZwei = true;
            boolean schrittVier = true;
            String text1 = "";
            String text2 = "";
            Enigma enigma= null;
            while (schrittZwei) {
                os.write("Bitte geben Sie den Pfad zu einer Konfiguration an:\n".getBytes());
                text1 = scan1.nextLine();
                Path path = Paths.get(text1);
                if (!Files.exists(path) || text1.isBlank()) {
                    os.write("Datei nicht gefunden!\n".getBytes());
                    continue;
                } else {
                    try {
                        enigma = new Enigma(new FileInputStream(text1));
                    } catch (EnigmaCreationException e) {
                        os.write("Fehlerhafte Enigma-Datei!\n".getBytes());
                        continue;
                    }
                }
                schrittZwei = false;
            }
            while (schrittVier) {
                os.write("Geben Sie ihren Ver- oder Entschlüsselten Text ein:\n".getBytes());
                text2 = scan1.nextLine();
                if (!text2.toLowerCase().equals(text2) || text2.isBlank() || !text2.matches("[a-z ]+")) {
                    os.write("Nicht erlaubte Symbole!\n".getBytes());
                    continue;
                }
                schrittVier = false;
            }


            InputStream in = new ByteArrayInputStream(text2.getBytes());
            String encrypt = enigma.encrypt(in);
            os.write("En- oder Dekodierter Text:".getBytes());
            os.write(encrypt.getBytes());
            os.write("\n".getBytes());
            os.write("Vielen Dank für die Nutzung der Enigma.\n".getBytes());
            os.flush();
        } catch (Exception e) {

        }

    }


}