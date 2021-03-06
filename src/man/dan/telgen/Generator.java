package man.dan.telgen;

public class Generator {
    public static void main(String[] args) throws Exception {
        try {
            if (args.length > 4 || args.length < 3) {
                System.out.println("No such count of arguments. Input 1) count of rows; 2) percent of lie strings [0, 100]; " +
                        "3) File to write; 4) File to right correctness (optional)");
                throw new Exception("Input error");
            }

            FileGen gen;

            if (args.length == 3)
                gen = new FileGen(Long.parseLong(args[0]), Integer.parseInt(args[1]), args[2]);
            else
                gen = new FileGen(Long.parseLong(args[0]), Integer.parseInt(args[1]), args[2], args[3]);

            gen.generate();

            System.out.println(args[0] + " rows are generated");
            if (args.length == 4)
                System.out.println("Answers are generated");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        System.out.println("Finish");
    }
}