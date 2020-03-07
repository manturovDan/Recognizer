package man.dan.jflex;

import java.util.ArrayList;
import java.util.HashMap;
%%

%class RecognizerJF
%unicode
%int
%debug
%line

%{
    private HashMap<String, Integer> statistics = new HashMap<String, Integer>();
    private ArrayList<String> curNums = new ArrayList<String>();

    void collectNumFAE(String num) {
        if (statistics.containsKey(num))
            statistics.put(num, statistics.get(num) + 1);
        else
            statistics.put(num, 1);
    }

    void addNum(String num) {
        curNums.add(num);
    }

    void clearNums() { curNums.clear(); }

    void saveNums() {
        for (String n : curNums) {
            collectNumFAE(n);
        }
    }
%}

%x NUMS_FT NUMS_S BODY DELIMETER_FT DELIMETER_S

%%
    <YYINITIAL> {
        ^(tel|fax): { clearNums(); yybegin(NUMS_FT); }
        ^sms: { clearNums(); yybegin(NUMS_S); }
        \n { return 0; }
        . {}
    }

    <NUMS_FT> {
        [0-9]{11} { addNum(yytext()); yybegin(DELIMETER_FT); }
        . { yybegin(YYINITIAL); }
        \n { yybegin(YYINITIAL); return 0; }
    }

    <DELIMETER_FT> {
        , { yybegin(NUMS_FT); }
        ;\n { yybegin(YYINITIAL); return 1; }
        . { yybegin(YYINITIAL); }
        \n { yybegin(YYINITIAL); return 0; }
    }


    <NUMS_S> {
        [0-9]{11} { addNum(yytext()); yybegin(DELIMETER_S); }
        . { yybegin(YYINITIAL); }
        \n { yybegin(YYINITIAL); return 0; }
    }

    <DELIMETER_S> {
        , { yybegin(NUMS_S); }
        ; { yybegin(BODY); }
        . { yybegin(YYINITIAL); }
        \n { yybegin(YYINITIAL); return 0; }
    }

    <BODY> {
        (\?body=([0-9a-zA-Z%,.!?]{1,64}))?\n { yybegin(YYINITIAL); return 1; }
        . { yybegin(YYINITIAL); }
    }

    <<EOF>> { System.out.println(statistics) ;return 3; }