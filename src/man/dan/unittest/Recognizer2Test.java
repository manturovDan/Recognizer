package man.dan.unittest;

import man.dan.smc.*;
import man.dan.telgen.FieldGenerator;
import man.dan.telgen.FieldGeneratorIncorrect;
import man.dan.telrec.Recognizer;
import man.dan.telrec.RegAnalyzer;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class Recognizer2Test {
    public void fileTCG(int count, String writeF, String ansF) {
        String[] answers = new String[count];
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(ansF))))) {
            String ans;
            int i = 0;
            while ((ans = br.readLine()) != null) {
                answers[i] = ans + i;
                i+=1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(writeF))))) {
            RecognizerCodegen regAnl = new RecognizerCodegen();
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                boolean res = regAnl.handle(line);
                //System.out.println(line + " - " + res);
                //System.out.println(answers[i] + " - " + Boolean.toString(res) + i + " " + line);
                Assert.assertEquals(answers[i], Boolean.toString(res) + i);
                //regAnl.handle(line);
                i+=1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fileT(int count, String writeF, String ansF) {
        String[] answers = new String[count];
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(ansF))))) {
            String ans;
            int i = 0;
            while ((ans = br.readLine()) != null) {
                answers[i] = ans + i;
                i+=1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(writeF))))) {
            RegAnalyzer regAnl = new RegAnalyzer();
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                boolean res = regAnl.handle(line);
                //System.out.println(line + " - " + res);
                //System.out.println(answers[i] + " - " + Boolean.toString(res) + i + " " + line);
                Assert.assertEquals(answers[i], Boolean.toString(res) + i);
                //regAnl.handle(line);
                i+=1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test200kFileCG() {
        fileTCG(200000, "200k/w200k", "200k/a200k");
    }

    @Test
    public void test200kFile() {
        fileT(200000, "200k/w200k", "200k/a200k");
    }

    @Test
    public void testOnFlight() {
        int COUNT = 100000;
        String row;
        Random random = new Random();

        FieldGenerator trueGen = new FieldGenerator();
        FieldGeneratorIncorrect falseGen = new FieldGeneratorIncorrect();
        RegAnalyzer regAnl = new RegAnalyzer();

        for (int i = 0; i < COUNT; ++i) {
            boolean answ = true;
            int prob = random.nextInt(100) + 1;
            if (prob <= 50) {
                row = falseGen.generate();
                answ = false;
            }
            else
                row = trueGen.generate();

            //System.out.println(row + " - " + regAnl.handle(row) + " - " + answ);
            Assert.assertEquals(regAnl.handle(row), answ);
        }

    }

    @Test
    public void testOnFlightCG() {
        int COUNT = 100000;
        String row;
        Random random = new Random();

        FieldGenerator trueGen = new FieldGenerator();
        FieldGeneratorIncorrect falseGen = new FieldGeneratorIncorrect();
        RecognizerCodegen regAnl = new RecognizerCodegen();

        for (int i = 0; i < COUNT; ++i) {
            boolean answ = true;
            int prob = random.nextInt(100) + 1;
            if (prob <= 50) {
                row = falseGen.generate();
                answ = false;
            }
            else
                row = trueGen.generate();

            //System.out.println(row + " - " + regAnl.handle(row) + " - " + answ);
            Assert.assertEquals(regAnl.handle(row), answ);
        }

    }
}