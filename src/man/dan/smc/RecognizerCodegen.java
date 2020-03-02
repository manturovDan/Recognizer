package man.dan.smc;

import java.util.ArrayList;

public class RecognizerCodegen {
    private RecognizerCodegenContext _fsm;
    private boolean _is_acceptable;

    private boolean bodyEn = false;
    private ArrayList<String> numbers = new ArrayList<String>();
    private StringBuilder currentPhone = new StringBuilder(11);
    private boolean inNumEntering = false;
    private int bodySize = 0;
    private boolean correct = false;

    public RecognizerCodegen() {
        _fsm = new RecognizerCodegenContext(this);
        _is_acceptable = false;
    }

    private StringBuilder headerBld = new StringBuilder(3);

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
        else return header.equals("fax") || header.equals("tel");
    }

    public void newNum() {
        inNumEntering = true;
        currentPhone.setLength(0);
    }

    public boolean AddNumCorrectness() {
        return inNumEntering && (currentPhone.length() < currentPhone.capacity());
    }

    public void addDigitToNum(int dig) {
        if (!inNumEntering || currentPhone.length() == currentPhone.capacity())
            return;
        currentPhone.append((char) dig);
    }

    public boolean endOfNum() {
        return inNumEntering && (currentPhone.length() == currentPhone.capacity());
    }

    public void saveNum() {
        if (!inNumEntering || currentPhone.length() == currentPhone.capacity())
            return;
        numbers.add(currentPhone.toString());
    }

    public boolean isBodyEn() { return bodyEn; }

    public boolean bodyInputEnable() { return bodySize < 64; }

    public void newBodySymbol() {
        if (bodySize == 64)
            return;
        ++bodySize;
    }

    public void rowCorrect() { correct = true; }

    public boolean handle(String row) {
        int l;
        int length;
        int code;
        char symb;

        _fsm.enterStartState();

        for (l = 0, length = row.length(); l < length; ++l) {
            symb = row.charAt(l);
            code = (int) symb;

            if (code >= 97 && code <= 122)
                _fsm.smallLetter();
            else if (code == 58)
                _fsm.colon();
            else if (code == 59)
                _fsm.semicolon();
            else if (code == 44)
                _fsm.comma();
            else if (code == 63)
                _fsm.question();
            else if (code == 61)
                _fsm.equal();
            else if (code >= 65 && code <= 90)
                _fsm.bigLetter();
            else if (code == 37 || code == 33 || code == 46)
                _fsm.percentOrExclamationoOrDot();
            else
                _fsm.error();
        }

        _fsm.EOS();

        return correct;
    }

    public String getHeader() {
        return headerBld.toString();
    }

}
