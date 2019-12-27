package ch.andiswiss;

/**
 * This is a Java interpreter for the programming language "Brainfuck", which is an amazing turing-complete
 * programming language consisting of 8 instructions:
 * >   Increment data pointer so that it points to next location in memory.
 * <   Decrement data pointer so that it points to previous location in memory.
 * +   Increment the byte pointed by data pointer by 1. If it is already at its maximum value, 255, then new value
 *     will be 0.
 * -   Decrement the byte pointed by data pointer by 1. If it is at its minimum value, 0, then new value will be 255.
 * .   Output the character represented by the byte at the data pointer.
 * ,   Read one byte and store it at the memory location pointed by data pointer.
 * [   If the byte pointed by data pointer is zero, then move instruction pointer to next matching ']',
 *     otherwise move instruction pointer to next command.
 * ]   If the byte pointed by data pointer is non-zero, then move instruction pointer to previous matching '[' command,
 *     otherwise to next command.
 * <p>
 * Licence: see separate license-file.
 *
 * @author AndiSwiss, https://www.andiswiss.ch
 * @version 1.0
 */
class Brainf__k {

    String code;

    public Brainf__k(String code) {
        this.code = code;
    }

    public String process(String input) {
        // Data is written to my custom class ByteArr:
        ByteArr data = new ByteArr();
        StringBuilder out = new StringBuilder();
        int inputPointer = 0;
        int codePointer = 0;

        if (code.length() == 0)
            return "";

        do {
            switch (code.charAt(codePointer)) {
            case '>':
                data.incrPointer();
                break;
            case '<':
                data.decrPointer();
                break;
            case '+':
                data.incrData();
                break;
            case '-':
                data.decrData();
                break;
            case '.':
                out.append(data.getData());
                break;
            case ',':
                if (input.length() <= inputPointer) {
                    // Note: This behaviour is necessary for the MorseCode-Translator code I've downloaded from
                    // the Brainfuck-Homepage -> see testMorseCodeTranslator()
                    data.setData((char) 0);
                } else {
                    data.setData(input.charAt(inputPointer));
                }
                ++inputPointer;
                break;
            case '[':
                if (data.getData() == 0) {
                    int count = 1;
                    do {
                        if (code.charAt(++codePointer) == ']')
                            --count;

                        // If you encounter other '[' while moving the pointer forward, then you have to also skip
                        //  the matching ']' !! -> increment the bracket-count:
                        if (code.charAt(codePointer) == '[')
                            ++count;
                    } while (count > 0);
                }
                break;
            case ']':
                if (data.getData() != 0) {
                    int count = 1;
                    do {
                        if (code.charAt(--codePointer) == '[')
                            --count;

                        // If you encounter other ']' while moving the pointer backwards, then you have to skip
                        // also the matching '[' !!  -> increment the bracket-count
                        if (code.charAt(codePointer) == ']')
                            ++count;
                    } while (count > 0);
                }
                break;
            default:
                // If any other element is in the code (such as a new line, a comment or anything), leave it out.
            }

            // Advance the code-pointer and check, whether the end of the code was reached!
            ++codePointer;
        } while (codePointer < code.length());

        return out.toString();
    }
}
