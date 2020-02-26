import java.io.File;

public class Generator {
    private long countRows;
    public static void main(String[] args) throws Exception {
        try {
            if (args.length > 4 || args.length < 3) {
                System.out.println("No such count of arguments. Input 1) count of rows; 2) percent of lie strings [0, 100]; " +
                        "3) File to write; 4) File to right correctness (optional)");
                throw new Exception("Input error");
            }

            long countRows = Long.parseLong(args[0]);
            int lie = Integer.parseInt(args[1]);
            if (lie < 0 || lie > 100)
                throw new ArithmeticException("Incorrect lie value");

            File rowsFile = new File(args[2]);
            File ansFile;

            if (args.length == 4) {
                ansFile = new File(args[3]);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        System.out.println("Start");

    }
}