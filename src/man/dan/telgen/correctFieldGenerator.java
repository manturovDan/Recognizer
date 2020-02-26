package man.dan.telgen;

import java.util.Random;

public class correctFieldGenerator {
    private mesType type;
    private String[] numbers;
    private String body;

    public static String generate() {
        Random random = new Random();

        mesType type = mesType.values()[random.nextInt(mesType.values().length)];

        return type.name();
    }
}
