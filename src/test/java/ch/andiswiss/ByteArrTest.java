package ch.andiswiss;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Some tests for the ByteArr-class
 * <p>
 * Licence: see separate license-file.
 *
 * @author AndiSwiss, https://github.com/AndiSwiss
 * @version 1.0
 */
class ByteArrTest {

    @Test public void byteArr1simple() {
        ByteArr b = new ByteArr();
        assertEquals(4, b.length());
        assertEquals(0, b.getData());
        b.incrData();
        assertEquals(1, b.getData());
    }

    @Test public void byteArr2overflow() {
        ByteArr b = new ByteArr();
        b.incrPointer();

        // checking overflow:
        b.decrData();
        b.decrData();
        assertEquals(254, b.getData());

        b.incrData();
        b.incrData();
        b.incrData();
        assertEquals(1, b.getData());
    }

    @Test public void byteArr3grow() {
        ByteArr b = new ByteArr();

        for (int i = 0; i < 3; i++)
            b.incrPointer();

        assertEquals(3, b.getPointerPosition());
        assertEquals(4, b.length());
        // Also, check, whether the given value is actually 0 (because this is stored internally as -128), and
        // filled with -128 when the array grows).
        assertEquals(0, b.getData());

        b.incrPointer();
        assertEquals(4, b.getPointerPosition());
        assertEquals(8, b.length());
        assertEquals(0, b.getData());

        for (int i = 0; i < 11; i++) {
            b.incrPointer();
        }
        assertEquals(15, b.getPointerPosition());
        assertEquals(16, b.length());
        assertEquals(0, b.getData());

        b.incrPointer();
        assertEquals(16, b.getPointerPosition());
        assertEquals(32, b.length());
        assertEquals(0, b.getData());
    }

    @Test public void byteArr4data() {
        ByteArr b = new ByteArr();

        b.setData('5');
        assertEquals('5', b.getData());

        b.incrPointer();
        assertEquals(1, b.getPointerPosition());

        b.incrPointer();

        // Write the word 'hello' from pos 1 to 5
        String hello = "hello";

        for (int i = 0; i < hello.length(); i++) {
            b.setData(hello.charAt(i));
            b.incrPointer();
        }

        assertEquals(7, b.getPointerPosition());

        // check the word 'hello' backwards:
        assertEquals(0, b.getData());
        b.decrPointer();
        assertEquals('o', b.getData());
        b.decrPointer();
        assertEquals('l', b.getData());
        b.decrPointer();
        assertEquals('l', b.getData());
        b.decrPointer();
        assertEquals('e', b.getData());
        b.decrPointer();
        assertEquals('h', b.getData());

        // Checking the full content of the array (NOTE: the 2nd and the last character is 0, so   in ASCII, or ((char) 0)
        System.out.println(b);
        assertEquals(
                "5" + ((char) 0) + "hello" + ((char) 0) + " -- Individual values: [53, 0, 104, 101, 108, 108, 111, 0]",
                b.toString());

    }

    @Test public void byteArr5errors() {
        ByteArr b = new ByteArr();

        try {
            b.decrPointer();
            fail();
        } catch (Exception e) {
            assertEquals("Pointer cannot be negative!", e.getMessage());
        }

        assertEquals(0, b.getPointerPosition());

        try {
            b.setData('≠');
            fail();
        } catch (Exception e) {
            assertEquals("Illegal value provided for saveData(..): ≠. Only values between 0 and 255 are allowed!",
                    e.getMessage());
        }
    }
}
