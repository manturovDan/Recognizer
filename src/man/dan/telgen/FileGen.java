package man.dan.telgen;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class FileGen {
    private long count;
    private int percent;
    private String write;
    private String ans;

    public FileGen(long _count, int _percent, String _write) {
        count = _count;
        percent = _percent;
        write = _write;

        if (percent < 0 || percent > 100 || count < 1)
            throw new ArithmeticException("Incorrect count or percent value");
    }

    public FileGen(long _count, int _percent, String _write, String _ans) {
        this(_count, _percent, _write);
        ans = _ans;
    }

    public void generate() throws IOException {
        PrintWriter writeF = new PrintWriter(write, "UTF-8");
        if (ans != null) {
            File ansF = new File(ans);
        }

        String row;
        Random random = new Random();

        for (int c = 0; c < count; ++c) {
            if (percent == 0) {
                row = correctFieldGenerator.generate();
            }
            else {
                int prob = random.nextInt(100) + 1;
                if (prob <= percent)
                    row = incorrectFieldGenerator.generate();
                else
                    row = correctFieldGenerator.generate();
            }

            writeF.println(row);
        }

        writeF.close();
    }
}
