package man.dan.telrec;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegAnalyzer {
    private static final String numbersReg = "((?:[0-9]{11},)*)([0-9]{11});";
    private static final String regExFT = "^(?:tel|fax):" + numbersReg + "$";
    private static final String regExSMS = "^sms:" + numbersReg + "(?:\\?body=(?:[0-9a-zA-Z%,.!?]{1,64}))?$";
    private static final String regExpFull = "^(tel|fax):((?:[0-9]{11},)*)([0-9]{11});$|^sms:((?:[0-9]{11},)*)([0-9]{11});(?:\\?body=(?:[0-9a-zA-Z%,.!?]{1,64}))?$";
    private Pattern patternFT;
    private Pattern patternSMS;
    private Pattern patternFull;

    private HashMap<String, Integer> statistics;

    public RegAnalyzer() {
        statistics = null;
        patternFT = Pattern.compile(regExFT);
        patternSMS = Pattern.compile(regExSMS);
        patternFull = Pattern.compile(regExpFull);
    }

    public RegAnalyzer(HashMap<String, Integer> stat) {
        this();
        statistics = stat;
    }

    public String toString() { return statistics.toString(); }

    public boolean handle(String row) {
        int match = 0; //0 - not match; 1 - FT match, 2 - SMS match
        boolean res = false;

        /*Matcher matchSc = patternFT.matcher(row);
        res = matchSc.matches();

        if (res)
            match = 1; // fax or tel
        else {
            matchSc = patternSMS.matcher(row);
            res = matchSc.matches();
            if (res)
                match = 2; //sms
        }*/

        Matcher matchSc = patternFull.matcher(row);
        res = matchSc.matches();

        if (res && statistics != null) {
            int tMatch;
            //System.out.println(matchSc.group(1));
            if (matchSc.group(1) != null && (matchSc.group(1).equals("tel") || matchSc.group(1).equals("fax")))
                tMatch = 2;
            else
                tMatch = 4;
            //System.out.println(row);
            //System.out.println(tMatch + "\n");

            String[] numbsPr = matchSc.group(tMatch).split(",");

            String[] numbs;

            if (numbsPr.length == 0 || (numbsPr.length == 1 && numbsPr[0].equals(""))) {
                numbs = new String[1];
            }
            else {
                numbs = new String[numbsPr.length + 1];
                System.arraycopy(numbsPr, 0, numbs, 0, numbsPr.length);
            }

            numbs[numbs.length-1] = matchSc.group(tMatch+1);

            for (String n : numbs) {
                if (statistics.containsKey(n))
                    statistics.put(n, statistics.get(n) + 1);
                else
                    statistics.put(n, 1);
            }
        }

        return res;
    }
}
