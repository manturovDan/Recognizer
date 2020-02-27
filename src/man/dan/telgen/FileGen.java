package man.dan.telgen;

import java.io.File;
import java.io.IOException;

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
        File writeF = new File(write);
        writeF.createNewFile();
        if (ans != null) {
            File ansF = new File(ans);
            ansF.createNewFile();
        }
    }
}
