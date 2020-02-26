package man.dan.telgen;

import java.util.Random;

public class StringGenerator {
    private static final String charLower = "abcdefghijklmnopqrstuvwxyz";
    private static final String charUpper = charLower.toUpperCase();
    private static final String charNumber = "0123456789";
    private static final String charSpec = "%,.!?;:&";


    public static String generateStr(int length, Random random, boolean upper, boolean lower, boolean number, boolean spec) {
        StringBuilder charRandom = new StringBuilder(70);
        if (upper) charRandom.append(charUpper);
        if (lower) charRandom.append(charLower);
        if (number) charRandom.append(charNumber);
        if (spec) charRandom.append(charSpec);

        if (charRandom.length() == 0)
            return "";

        if (length < 0) throw new IllegalArgumentException();

        StringBuilder body = new StringBuilder(length);

        for (int i = 0; i < length; ++i) {
            body.append(charRandom.charAt(random.nextInt(charRandom.length())));
        }

        return body.toString();
    }
}
