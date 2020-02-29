package man.dan.telrec;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Recognizer {
    public static void main(String[] args) throws Exception {
        if (args.length != 1 && args.length != 2)
            throw new Exception("Invalid argument count");

        InputStream target = null;
        try {
            target = new FileInputStream(new File(args[0]));
        } catch (IOException e) {
            System.out.println("Error creating Input Stream");
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(target))) {
            String line;
            HashMap<String, Integer> stat = new HashMap<String, Integer>();

            RegAnalyzer regAnl = new RegAnalyzer(stat);
            while ((line = br.readLine()) != null) {
                //System.out.println(line + " - " + regAnl.handle(line));
                regAnl.handle(line);
            }

            if (args.length == 2) {
                try {
                    PrintWriter writeStat = new PrintWriter(args[1], StandardCharsets.UTF_8);
                    writeStat.println(stat);
                    writeStat.close();
                    System.out.println("Statistics was saved");
                } catch (Exception e) {
                    System.out.println("Error writing statistics to file. Writing to System.out");
                    System.out.println(stat);
                }
            }
            else {
                System.out.println(stat);
            }
        } catch (IOException e) {
            System.out.println("File reading error");
        }
    }
}
