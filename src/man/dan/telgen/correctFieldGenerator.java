package man.dan.telgen;

import java.util.Random;

public class correctFieldGenerator {
    private static final int numsMax = 20;
    private static final int numsSomeBorder = 10;
    private static final int bodyProbDen = 2;
    private static final int bodyProbNum = 1;
    private static Random random = new Random();

    public static String generate() {
        StringBuilder result = new StringBuilder();

        mesType type = mesType.values()[random.nextInt(mesType.values().length)];
        result.append(type).append(":");

        int numTels = random.nextInt(numsMax) + 1;
        if (numTels > numsSomeBorder) numTels = 1;

        String[] nums = new String[numTels];
        for (int i = 0; i < numTels; ++i)
            nums[i] = StringGenerator.generateStr(11, random, false, false, true, false);

        result.append(String.join(",", nums));
        result.append(";");

        if (type == mesType.sms) {
            if(random.nextInt(bodyProbDen) <= bodyProbNum) {
                result.append("?body=").append(StringGenerator.generateStr(random.nextInt(64) + 1, random, true, true, true, true));
            }
        }

        return result.toString();
    }
}
