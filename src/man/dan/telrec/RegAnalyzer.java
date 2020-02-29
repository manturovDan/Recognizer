package man.dan.telrec;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegAnalyzer {
    private static final String numbersReg = "((?:[0-9]{11},)*)([0-9]{11});";
    private static final String regExFT = "^(?:tel|fax):" + numbersReg;
    private static final String regExSMS = "^sms:" + numbersReg + "(?:\\?body=([0-9a-zA-Z%,.!?]*))?$";
    private Pattern patternFT;
    private Pattern patternSMS;;

    private HashMap<String, Integer> statistics;

    public RegAnalyzer() {
        statistics = null;
        patternFT = Pattern.compile(regExFT);
        patternSMS = Pattern.compile(regExSMS);
    }

    public RegAnalyzer(HashMap<String, Integer> stat) {
        this();
        statistics = stat;
    }

    public String toString() { return statistics.toString(); }

    public boolean handle(String row) {
        int match = 0; //0 - not match; 1 - FT match, 2 - SMS match
        boolean res = false;

        Matcher matchFT = patternFT.matcher(row);
        res = matchFT.matches();

        if (res)
            match = 1;
        else {
            Matcher matchSMS = patternSMS.matcher(row);
            res = matchSMS.matches();
            if (res)
                match = 2;
        }

        if (res && statistics != null) {

        }

        return res;
    }
}
