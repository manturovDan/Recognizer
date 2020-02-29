package man.dan.telgen;

public class FieldGeneratorIncorrect extends FieldGenerator {
    private static final String charBan = "абвгдеёжзиклмнАПЫЫПЫВОЕ_وزردسکېسرهورکړئשלםלמכשףעמטבע";

    protected String catGen(int happening, StringBuilder bldr) {
        if ((happening & 0b000001) == 0b000001) { //0) illegal type
            String typeMsg;
            do {
                typeMsg = StringGenerator.generateStr(random.nextInt(20), random, true, true,true, true);
            } while (typeMsg.equals("tel") || typeMsg.equals("fax") || typeMsg.equals("sms"));
            bldr.append(typeMsg);
            return typeMsg;
        }
        else
            return super.catGen(bldr);

    }

    private void colonGen(int happening, StringBuilder bldr) {
        if ((happening & 0b000010) == 0b000010) { //1) something instead of :
            String delimiter = StringGenerator.generateStr(1, random, false, false,false, true);
            if (!delimiter.equals(":"))
                bldr.append(delimiter);
        }
        else
            super.colonGen(bldr);
    }

    protected int getNumSize(int hap) {
        int telSize = 11;
        if ((hap & 0b00001000) == 0b00001000)  //3) number contains not 11 digits
            telSize = random.nextInt(20);
        return telSize;
    }

    private static String buildNums(int happening, String[] numsPlace) {
        if ((happening & 0b010000) == 0b010000) { //4) something instead of ,
            String build = new String(String.join(",", numsPlace));
            return build.replaceAll("[" + StringGenerator.charSpec + "]]", ",");
        }
        else
            return String.join(",", numsPlace);
    }

    protected void numsGen(int happening, StringBuilder bldr) {
        int numTels = random.nextInt(numsTop) + 1;
        if (numTels > numsBorder) numTels = 1;


        boolean upper = false;
        boolean lower = false;
        boolean spec = false;

        if ((happening & 0b000100) == 0b000100) { //2) there are illegal symbols in a number (or not numbers)
            numTels = random.nextInt(numsBorder);

            upper = random.nextBoolean();
            lower = random.nextBoolean();
            spec = random.nextBoolean();

            if (!upper && !lower && !spec)
                upper = lower = spec = true;
        }

        String[] nums = new String[numTels];
        for (int i = 0; i < numTels; ++i)
            nums[i] = StringGenerator.generateStr(getNumSize(happening), random, upper, lower, true, spec);

        bldr.append(buildNums(happening, nums));
    }

    protected void semicolonGen(int happening, StringBuilder bldr) {
        if ((happening & 0b100000) == 0b100000) { //5) something instead of ;
            String delimiter = StringGenerator.generateStr(1, random, false, false,false, true);
            if (!delimiter.equals(";"))
                bldr.append(delimiter);
        }
        else
            super.semicolonGen(bldr);
    }

    protected void bodyGen (int happening, StringBuilder bldr, String cat) {
        int bodyEr;
        if (happening == 0) {
            if (cat.equals("sms"))
                bodyEr = random.nextInt(3) + 1;
            else
                bodyEr = random.nextInt(4);


        }
        else {
            if (random.nextBoolean()) // or not generate
                bodyEr = random.nextInt(4);
            else
                return;
        }

        badBody(bodyEr, bldr);
    }

    protected void badBody(int bodyEr, StringBuilder bldr) {
        if ((bodyEr & 0b01) == 0b01) { //0) ?=
            String delimiter = StringGenerator.generateStr(10, random, true, true,true, true);
            if (!delimiter.equals("?body="))
                bldr.append(delimiter);
        }
        else
            bldr.append("?body=");


        if ((bodyEr & 0b10) == 0b10) {
            if (random.nextInt(10) == 1)
                bldr.append(StringGenerator.generateStr(random.nextInt(64) + 65, random, true, true, true, true));
            else {
                StringBuilder bodyBld = new StringBuilder(StringGenerator.generateStr(random.nextInt(64), random, true, true,true, true));

                if (bodyBld.length() > 0) {
                    for (int i = 0; i < random.nextInt(20) + 2; ++i) {
                        int repl = random.nextInt(bodyBld.length());
                        String newc = String.valueOf(charBan.charAt(random.nextInt(charBan.length())));
                        bodyBld.replace(repl, repl + 1, newc);
                    }

                    bldr.append(bodyBld);
                }
            }
        }

        if (bodyEr == 0) {
            super.bodyGen(bldr);
        }
    }

    public String generate() {

        /*
            There are 6 common types of error in rows:
            0) illegal type                                              000001
            1) something instead of :                                    000010
            2) there are illegal symbols in a number (or not numbers)    000100
            3) number contains not 11 digits                             001000
            4) ,                                                         010000
            5) ;                                                         100000
         */

        int common = random.nextInt(64);
        StringBuilder bldr = new StringBuilder();

        String cat = catGen(common, bldr);
        colonGen(common, bldr);
        numsGen(common, bldr);
        semicolonGen(common, bldr);

        /*
            There two three types of error in body
            0) ?body=
            1) there are illegal symbols in the body
         */

        bodyGen(common, bldr, cat);

        return bldr.toString();
    }
}
