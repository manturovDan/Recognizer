package man.dan.jflex;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.nio.charset.StandardCharsets;
%%

%class RecognizerJF
%unicode
%int
%line

%{
    private static HashMap<String, Integer> statistics = new HashMap<String, Integer>();
    private static ArrayList<String> curNums = new ArrayList<String>();

    private static void collectNumFAE(String num) {
        if (statistics.containsKey(num))
            statistics.put(num, statistics.get(num) + 1);
        else
            statistics.put(num, 1);
    }

    private static void addNum(String num) {
        curNums.add(num);
    }

    private static void clearNums() { curNums.clear(); }

    private static void saveNums() {
        for (String n : curNums) {
            collectNumFAE(n);
        }
    }

    public static void main(String[] args) throws Exception {
        long m = System.currentTimeMillis();
        if (args.length != 1 && args.length != 2)
            throw new Exception("Invalid argument count");


        String encodingName = "UTF-8";
        try {
            java.nio.charset.Charset.forName(encodingName); // Side-effect: is encodingName valid?
        } catch (Exception e) {
            System.out.println("Invalid encoding '" + encodingName + "'");
            return;
        }

        RecognizerJF scanner = null;
        try {
            java.io.FileInputStream stream = new java.io.FileInputStream(args[0]);
            java.io.Reader reader = new java.io.InputStreamReader(stream, encodingName);
            scanner = new RecognizerJF(reader);
            int retStatus;
            do {
                retStatus = scanner.yylex();
                if (retStatus == 1)
                    saveNums();

                clearNums();
                //System.out.println(retStatus);
            } while (!scanner.zzAtEOF);

        }
        catch (java.io.FileNotFoundException e) {
            System.out.println("File not found : \""+args[0]+"\"");
        }
        catch (java.io.IOException e) {
            System.out.println("IO error scanning file \""+args[0]+"\"");
            System.out.println(e);
        }
        catch (Exception e) {
            System.out.println("Unexpected exception:");
            e.printStackTrace();
        }


        if (args.length == 2) {
            try {
                PrintWriter writeStat = new PrintWriter(args[1], StandardCharsets.UTF_8);
                writeStat.println(statistics);
                writeStat.close();
                System.out.println("Statistics was saved");
            } catch (Exception e) {
                System.out.println("Error writing statistics to file. Writing to System.out");
                System.out.println(statistics);
            }
        }
        else {
            System.out.println(statistics);
        }

        System.out.println("Time of processing: " + ((double) (System.currentTimeMillis() - m))/1000 + " seconds");

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

    <<EOF>> { return 3; }