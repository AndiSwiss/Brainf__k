package ch.andiswiss;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Some tests for the Brainf__k-class
 * <p>
 * Some of the Brainfuck-code-examples used in these tests originate from:
 * - https://en.wikipedia.org/wiki/Brainfuck
 * - http://www.bf.doleczek.pl/
 * <p>
 * Licence: see separate license-file. For licenses of used code-examples, see their licenses accordingly.
 *
 * @author AndiSwiss, https://github.com/AndiSwiss
 * @version 1.0
 */
class Brainf__kTest {
    /**
     * Multiplication of two numbers.
     * Note: Only positive integers allowed. Result must be in range 0-255.
     */
    @Test public void testTwoNumbersMultiplier() {
        String code = ",>,<[>[->+>+<<]>>[-<<+>>]<<<-]>>.";
        final char[] input = { 9, 8 };
        assertThat(new Brainf__k(code).process("" + input[0] + input[1]),
                is(String.valueOf((char) (input[0] * input[1]))));

        input[0] = 8;
        input[1] = 31;
        assertThat(new Brainf__k(code).process("" + input[0] + input[1]),
                is(String.valueOf((char) (input[0] * input[1]))));
    }

    /**
     * Addition of two numbers
     * Note: Only positive integers allowed. Result must be in range 0-255.
     */
    @Test public void testTwoNumbersAddition() {
        String code = ",>,[<+>-]<.";
        int n1 = 4, n2 = 5, result = 9;
        assertThat(new Brainf__k(code).process("" + (char) n1 + (char) n2), is("" + (char) result));

        n1 = 4;
        n2 = 250;
        result = 254;
        assertThat(new Brainf__k(code).process("" + (char) n1 + (char) n2), is("" + (char) result));
    }

    /**
     * Subtraction of two numbers
     * Note: Only positive integers allowed. Result must be in range 0-255.
     */
    @Test public void testTwoNumbersSubtraction() {
        String code = ",>,[<->-]<.";
        int n1 = 8, n2 = 2, result = 6;
        assertThat(new Brainf__k(code).process("" + (char) n1 + (char) n2), is("" + (char) result));

        n1 = 254;
        n2 = 249;
        result = 5;
        assertThat(new Brainf__k(code).process("" + (char) n1 + (char) n2), is("" + (char) result));
    }

    /**
     * The following test checks for correct negative overflow handling:
     */
    @Test public void testNegativeOverflow() {
        assertThat(new Brainf__k("-.").process(""), is("" + (char) 255));
    }

    /**
     * The following test checks for correct positive overflow handling:
     */
    @Test public void testPositiveOverflow() {
        assertThat(new Brainf__k(",+.").process("" + (char) 255), is("" + (char) 0));
    }

    /**
     * The following test checks for correct positive overflow handling while adding numbers:
     */
    @Test public void testAdditionPositiveOverflow() {
        String code = ",>,[<+>-]<.";
        int n1 = 10, n2 = 250, result = 4;
        assertThat(new Brainf__k(code).process("" + (char) n1 + (char) n2), is("" + (char) result));
    }

    /**
     * Hello world-example from https://en.wikipedia.org/wiki/Brainfuck#Hello_World!
     */
    @Test public void testHelloWorld() {
        String code = "++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.";
        assertThat(new Brainf__k(code).process(""), is("Hello World!\n"));

    }

    /**
     * Example from Brainfuck-Homepage: "http://www.bf.doleczek.pl/"
     * This actually fully works (as stated on the homepage: only use capital letters!)
     * <p>
     * I have used the 'expected' from https://gc.de/gc/morse/ for verifying the code. It is the the same, except it
     * has more spaces for the 'space'.
     * And it adds a final character '.-.-.', which is actually the official end mark for a morse code message!
     */
    @Test public void testMorseCodeTranslator() {
        try {
            String file = "src/main/resources/Kata20_MorseCode_Translator.txt";
            String code = new String(Files.readAllBytes(Paths.get(file)));
            String text = "ANDI CODES".toUpperCase();
            String answer = new Brainf__k(code).process(text);
            System.out.println("\n'" + text + "' in morse code is: " + answer);

            // Verification using an official morse code alphabet
            String fileAlphabet = "src/main/resources/Kata20_MorseCodeAlphabet.txt";
            List<String> alphabet = Files.readAllLines(Paths.get(fileAlphabet));

            StringBuilder expected = new StringBuilder();

            text.chars() // char-stream
                    .map(c -> c - 'A' + 1) // get a number from the letter -> is the line I have to use in the alphabet
                    .filter(c -> c == -32 || c > 0 && c < 28) // filter out negative values or too high values
                    .forEach(c -> {
                        if (c == -32)
                            expected.append("      ");
                        else
                            expected.append(alphabet.get(c).substring(2)).append(' ');
                    });
            // append the official morse code end-character: "+" or ".-.-.":
            expected.append(".-.-.");

            //            String expected = ".- -. -.. ..       -.-. --- -.. . ... .-.-.";
            assertEquals(expected.toString(), answer);

        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Example from Brainfuck-Homepage: "http://www.bf.doleczek.pl/"
     * Note: at the end, you have to add a carriage return '\r', because this is how the interactive version works
     * online. The returning value then also consists of the original string AND a carriage return AND the answer.
     */
    @Test public void testReverse() {
        String code =
                "[Echo, promyk.doleczek.pl]\n" + ">+[[>],.----- ----- ---[+++++ +++++ +++[<]]>]\n" + "<<[<]>[-]>[>]<\n"
                        + "[.<]";
        assertThat(new Brainf__k(code).process("Andi is coding!\r"), is("Andi is coding!\r!gnidoc si idnA"));
    }

}
