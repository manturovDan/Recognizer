package man.dan.telgen;

import java.util.Random;

/*
    There are 8 types of error in rows:
    0) illegal type                                              00000001
    1) something instead of :                                    00000010
    2) there are illegal symbols in a number (or not numbers)    00000100
    3) number contains not 11 digits                             00001000
    4) ,                                                         00010000
    5) ?=                                                        00100000
    6) inconsistency of body and type                            01000000
    7) there are illegal symbols in the body                     10000000

    The number [1, 255] generates, combination of errors determines by masks
*/
public class incorrectFieldGenerator {
    private static Random random = new Random();

    private static int getNumSize(int hap) {
        int telSize = 11;
        if ((hap & 0b00001000) == 0b00001000)  //3) number contains not 11 digits
            telSize = random.nextInt(20);
        return telSize;
    }

    public static String generate() {
        int happening = random.nextInt(254) + 1;
        StringBuilder allRes = new StringBuilder();
        String[] numsPlace;
        boolean sBody = false;

        if ((happening & 0b00000001) == 0b00000001) {  //0) illegal type
            String typeMsg;
            do {
                typeMsg = StringGenerator.generateStr(random.nextInt(20), random, true, true,true, true);
            } while (typeMsg.equals("tel") || typeMsg.equals("fax") || typeMsg.equals("sms"));
            allRes.append(typeMsg);
        }
        else
            allRes.append(mesType.values()[random.nextInt(mesType.values().length)].name());


        if ((happening & 0b00000010) == 0b00000010) { //1) something instead of :
            String delimiter = StringGenerator.generateStr(1, random, false, false,false, true);
            if (!delimiter.equals(";"))
                allRes.append(delimiter);
        }
        else
            allRes.append(":");

        if ((happening & 0b00000100) == 0b00000100) { //2) there are illegal symbols in a number (or not numbers)
            int numsCount = random.nextInt(11);
            numsPlace = new String[numsCount];
            for (int n = 0; n < numsCount; ++n)
                numsPlace[n] = StringGenerator.generateStr(getNumSize(happening), random, true, true, true, true);
        }
        else {
            int numsCount = random.nextInt(10) + 1;
            numsPlace = new String[numsCount];
            for (int n = 0; n < numsCount; ++n)
                numsPlace[n] = StringGenerator.generateStr(getNumSize(happening), random, false, false, true, false);
        }

        if ((happening & 0b00010000) == 0b00010000) { //4) something instead of ,
            StringBuilder buildNums = new StringBuilder(String.join(",", numsPlace));
            System.out.println(buildNums);
            int comma = -1;
            do {
                comma = buildNums.indexOf(",", comma + 1);
                System.out.println("Comma: " + comma);
            } while (comma != -1);
        }
        else
            allRes.append(String.join(",", numsPlace));


        if ((happening & 0b00100001) == 0b00100001) {
            //System.out.println(5);
        }
        if ((happening & 0b01000001) == 0b01000001) {
            //System.out.println(6);
        }
        if ((happening & 0b10000001) == 0b10000001) {
            //System.out.println(7);
        }

        return allRes.toString();
    }
}
