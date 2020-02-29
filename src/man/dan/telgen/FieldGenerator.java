package man.dan.telgen;

import java.util.Random;

public class FieldGenerator {
    protected static final int numsTop = 20;
    protected static final int numsBorder = 10;
<<<<<<< HEAD
    protected static final int bodyProbDen = 2;
    protected static final int bodyProbNum = 1;
    protected static Random random = new Random();

    protected static String catGen(StringBuilder bldr) {
=======
    protected static final int bodyProbDen = 3;
    protected static final int bodyProbNum = 1;
    protected static Random random = new Random();

    protected String catGen(StringBuilder bldr) {
>>>>>>> origin/generator-deb
        String cat = String.valueOf(mesType.values()[random.nextInt(mesType.values().length)]);
        bldr.append(cat);
        return cat;
    }

<<<<<<< HEAD
    protected static void colonGen(StringBuilder bldr) {
        bldr.append(":");
    }

    protected static void numsGen(StringBuilder bldr) {
=======
    protected void colonGen(StringBuilder bldr) {
        bldr.append(":");
    }

    protected void numsGen(StringBuilder bldr) {
>>>>>>> origin/generator-deb
        int numTels = random.nextInt(numsTop) + 1;
        if (numTels > numsBorder) numTels = 1;

        String[] nums = new String[numTels];
        for (int i = 0; i < numTels; ++i)
            nums[i] = StringGenerator.generateStr(11, random, false, false, true, false);

        bldr.append(String.join(",", nums));
    }

<<<<<<< HEAD
    protected static void semicolonGen(StringBuilder bldr) {
        bldr.append(":");
    }

    protected static void bodyGen(StringBuilder bldr) {
=======
    protected void semicolonGen(StringBuilder bldr) {
        bldr.append(";");
    }

    protected void bodyGen(StringBuilder bldr) {
>>>>>>> origin/generator-deb
        if(random.nextInt(bodyProbDen) <= bodyProbNum) {
            bldr.append("?body=").append(StringGenerator.generateStr(random.nextInt(64) + 1, random, true, true, true, true));
        }
    }

<<<<<<< HEAD
    public static String generate() {
=======
    public String generate() {
>>>>>>> origin/generator-deb
        StringBuilder bldr = new StringBuilder();
        catGen(bldr);
        colonGen(bldr);
        numsGen(bldr);
        semicolonGen(bldr);
        bodyGen(bldr);

        return bldr.toString();
    }
}
