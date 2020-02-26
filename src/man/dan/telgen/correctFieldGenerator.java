package man.dan.telgen;

import java.util.Random;

public class correctFieldGenerator {
    private static final int numsMax = 20;
    private static final int numsSomeBorder = 10;
    private static final int bodyProbDen = 2;
    private static final int bodyProbNum = 1;
    private static final String charLower = "abcdefghijklmnopqrstuvwxyz";
    private static final String charUpper = charLower.toUpperCase();
    private static final String number = "0123456789";
    private static final String charSpec = "%,.!?;:&";
    private static final String charRandom = charLower + charUpper + number + charSpec;
    private static Random random = new Random();

    public static String generateBody(int length) {
        if (length < 1) throw new IllegalArgumentException();

        StringBuffer body = new StringBuffer(length);

        for (int i = 0; i < length; ++i) {
            body.append(charRandom.charAt(random.nextInt(charRandom.length())));
        }

        return body.toString();
    }

    public static String generate() {
        StringBuilder result = new StringBuilder();

        mesType type = mesType.values()[random.nextInt(mesType.values().length)];
        result.append(type).append(":");

        int numTels = random.nextInt(numsMax) + 1;
        if (numTels > numsSomeBorder) numTels = 1;

        String[] nums = new String[numTels];
        for (int i = 0; i < numTels; ++i) {
            StringBuilder oneNum = new StringBuilder(11);
            for(int d = 0; d < 11; ++d) {
                oneNum.append(random.nextInt(10));
            }
            nums[i] = oneNum.toString();
        }

        result.append(String.join(",", nums));
        result.append(";");

        if (type == mesType.sms) {
            if(random.nextInt(bodyProbDen) <= bodyProbNum) {
                result.append("?body=").append(generateBody(random.nextInt(64) + 1));
            }
        }

        return result.toString();
    }
}
