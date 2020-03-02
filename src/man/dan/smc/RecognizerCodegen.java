package man.dan.smc;

import man.dan.telrec.Recognizer;

public class RecognizerCodegen {
    private RecognizerCodegenContext _fsm;
    private boolean _is_acceptable;

    private boolean bodyEn = false;

    public RecognizerCodegen() {
        _fsm = new RecognizerCodegenContext(this);
        _is_acceptable = false;
    }

    private StringBuilder headerBld = new StringBuilder(10);

    public boolean addToHead(char symb) {
        if (headerBld.length() != headerBld.capacity()) {
            headerBld.append(symb);
            return true;
        }

        return false;
    }

    public boolean isPlaceInHeader() {
        return headerBld.length() < headerBld.capacity();
    }


    public boolean correctHeader() {
        String header = headerBld.toString();
        if (header.equals("sms")) {
            bodyEn = true;
            return true;
        }
        else if(header.equals("fax") || header.equals("tel"))
            return false;
    }

    public boolean handle(String row) {
        int l;
        int length;
        int code;
        char symb;

        _fsm.enterStateStae();

        for (l = 0, length = row.length(); l < length; ++l) {
            symb = row.charAt(l);
            code = (int) symb;
            if (code >= 97 && code <= 122) {
                _fsm.smallLetter();
            }
        }
    }

    public String getHeader() {
        return headerBld.toString();
    }

}
