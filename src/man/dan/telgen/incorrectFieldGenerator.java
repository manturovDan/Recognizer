package man.dan.telgen;

import java.util.Random;

/*
    There are 8 types of error in rows:
    0) illegal type                                              000000001
    1) something instead of :                                    000000010
    2) there are illegal symbols in a number (or not numbers)    000000100
    3) number contains not 11 digits                             000001000
    4) ,                                                         000010000
    5) ;                                                         000100000
        if there is body:
    6) ?=                                                        001000000
    7) inconsistency of body and type  (just exists body)        010000000
    8) there are illegal symbols in the body                     100000000

    The number [1, 255] generates, combination of errors determines by masks
*/

public class incorrectFieldGenerator {
    private static Random random = new Random();
    private static final String charBan = "абвгдеёжзиклмнАПЫЫПЫВОЕ_ وزر د سکې سره ورکړئ שלם למכשף עם מטבע";

    private static int getNumSize(int hap) {
        int telSize = 11;
        if ((hap & 0b00001000) == 0b00001000)  //3) number contains not 11 digits
            telSize = random.nextInt(20);
        return telSize;
    }

    private static String colon(int happening) {
        if ((happening & 0b000000010) == 0b000000010) { //1) something instead of :
            String delimiter = StringGenerator.generateStr(1, random, false, false,false, true);
            if (!delimiter.equals(":"))
                return delimiter;
            else
                return "";
        }
        else
            return ":";
    }

    private static String[] getNums(int happening) {
        String[] numsPlace;
        if ((happening & 0b000000100) == 0b000000100) { //2) there are illegal symbols in a number (or not numbers)
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

        return numsPlace;
    }

    private static String buildNums(int happening, String[] numsPlace) {
        if ((happening & 0b000010000) == 0b000010000) { //4) something instead of ,
            StringBuilder build = new StringBuilder(String.join(",", numsPlace));
            boolean correct = false;

            while(!correct) {
                int comma = -1;
                while (true) {
                    comma = build.indexOf(",", comma + 1);
                    if (comma == -1)
                        break;
                    String repl = StringGenerator.generateStr(random.nextInt(3) + 1, random, true, true, false, true);

                    if (!repl.equals(","))
                        correct = true;

                    build.replace(comma, comma + 1, repl);
                }
            }

            return build.toString();
        }
        else
            return String.join(",", numsPlace);
    }

    private static String semicolon(int happening) {
        if ((happening & 0b000100000) == 0b000100000) { //5) ;
            String delimiter = StringGenerator.generateStr(random.nextInt(3) + 1, random, false, false,false, true);
            if (!delimiter.equals(";'"))
                return delimiter;
            else
                return "";
        }
        else
            return ";";
    }

    private static String bodyConstructor(int happening) {
        StringBuilder result = new StringBuilder("");
        if ((happening & 0b010000000) == 0b010000000) { //7) inconsistency of body and type  (just exists body)
            if ((happening & 0b001000000) == 0b001000000) { //6) ?=
                String delimiter = StringGenerator.generateStr(random.nextInt(5) + 1, random, false, false,false, true);
                if (!delimiter.equals("?="))
                    result.append(delimiter);
            }
            else
                result.append("?=");

            if ((happening & 0b100000000) == 0b100000000) { //7) there are illegal symbols in the body
                int size = random.nextInt(66);
                StringBuilder banStr = new StringBuilder(size);
                for (int i = 0; i < size; ++i) {
                    banStr.append(charBan.charAt(random.nextInt(charBan.length())));
                }

                result.append(banStr);
            }
            else
                result.append(StringGenerator.generateStr(random.nextInt(63) + 1, random, true, true, true, true));

        }

        return result.toString();
    }

    public static String generate() {
        int happening = random.nextInt(510) + 1;
        if (happening == 511 + 127) happening += 255; //to avoid the situation when body is absent and errors are in this field only

        StringBuilder allRes = new StringBuilder();
        String[] numsPlace;

        if ((happening & 0b000000001) == 0b000000001) {  //0) illegal type
            String typeMsg;
            do {
                typeMsg = StringGenerator.generateStr(random.nextInt(20), random, true, true,true, true);
            } while (typeMsg.equals("tel") || typeMsg.equals("fax") || typeMsg.equals("sms"));
            allRes.append(typeMsg);
        }
        else {
            allRes.append(mesType.values()[random.nextInt(mesType.values().length)].name());
            if (allRes.toString().equals("sms") && happening == 128) //no error
                happening = 130; // when type is sms body exists and if only 6th bit is one it is not error
        }


        allRes.append(colon(happening));
        numsPlace = getNums(happening);
        allRes.append(buildNums(happening, numsPlace));
        allRes.append(semicolon(happening));
        allRes.append(bodyConstructor(happening));


        return allRes.toString();
    }
}
