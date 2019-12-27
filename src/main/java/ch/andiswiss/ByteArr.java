package ch.andiswiss;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A byte array, which can be used for the Brainf__k-Java-interpreter.
 * <p>
 * - The data range (externally) is from 0 to 255, though internally, the data is saved as normal bytes
 *   (range -128 to +127). The offset will be calculated automatically by the methods of this class.
 * - A pointer can be moved forward/backward (the data-array will grow automatically if necessary).
 * - Data can be saved or read at the current pointer position.
 * <p>
 * Licence: see separate license-file.
 *
 * @author AndiSwiss, https://www.andiswiss.ch
 * @version 1.0
 */
public class ByteArr {
    private byte[] bytes;
    private int pointer;

    /**
     * Creates a small byte-array. It will grow, if necessary (see method incrPointer())
     * This byte-array will be filled with 0 (internally with -128, because of the offset).
     */
    public ByteArr() {
        bytes = new byte[4];
        Arrays.fill(bytes, (byte) -128);
        pointer = 0;
    }

    /**
     * Increments data at the current pointer position (with correct overflow behaviour)
     */
    protected void incrData() {
        ++bytes[pointer];
    }

    /**
     * Decrements data at the current pointer position (with correct overflow behaviour)
     */
    protected void decrData() {
        --bytes[pointer];
    }

    /**
     * Increments the pointer position. If the pointer is suddenly pointing outside of the array, the
     * array size will be doubled (so array-size grows from 4 -> 8 -> 16 -> ...)
     */
    protected void incrPointer() {
        ++pointer;
        if (pointer > bytes.length - 1) {
            // Double the capacity of the array:
            bytes = Arrays.copyOf(this.bytes, bytes.length * 2);
            Arrays.fill(bytes, bytes.length / 2, bytes.length, (byte) -128);
        }
    }

    /**
     * Decreases the pointer position. If the pointer is suddenly pointing at a negative value, an error is thrown.
     */
    protected void decrPointer() {
        if (pointer == 0) {
            throw new IllegalArgumentException("Pointer cannot be negative!");
        }
        --pointer;
    }

    /**
     * Saves data at the current pointer position.
     * Note: Internally, the values are saved as a regular byte with range -128 to +127.
     *       The necessary offset is calculated automatically.
     *
     * @param data a char (value between 0 and 255)
     */
    protected void setData(char data) {
        // NOTE: A char can never be negative.
        if (data > 255)
            throw new IllegalArgumentException("Illegal value provided for saveData(..): " + data
                    + ". Only values between 0 and 255 are allowed!");
        bytes[pointer] = (byte) (data - 128);
    }

    /**
     * Reads data at the current pointer position.
     * Note: Internally, the values are saved as a regular byte with range -128 to +127.
     *       The necessary offset is calculated automatically.
     *
     * @return char (value between 0 and 255)
     */
    protected char getData() {
        return (char) (bytes[pointer] + 128);
    }

    /**
     * For testing purposes only.
     *
     * @return length of the internal array.
     */
    protected int length() {
        return bytes.length;
    }

    /**
     * For testing purposes only.
     *
     * @return position of the pointer.
     */
    protected int getPointerPosition() {
        return pointer;
    }

    /**
     * For testing purposes only.
     */
    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        List<Short> values = new ArrayList<>();
        for (byte b : bytes) {
            sb.append((char) (b + 128));
            values.add((short) (b + 128));
        }
        return sb.toString() + " -- Individual values: " + values;
    }
}
