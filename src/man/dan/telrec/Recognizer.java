package man.dan.telrec;

import java.io.*;
import java.util.HashMap;

public class Recognizer {
    public static void main(String[] args) throws Exception {
        if (args.length != 1)
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
                System.out.println(line + " - " + regAnl.handle(line));
            }

            System.out.println(stat);
        } catch (IOException e) {
            System.out.println("File reading error");
        }
    }
}
