package com.skadodle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {

    @Test
    void byteArrayToIntTest() {
        byte[] number10 = new byte[] {0, 0, 0, 0xA};
        byte[] number11 = new byte[] {0, 0, 0, 0xB};
        byte[] number50 = new byte[] {0, 0, 0, 0x32};
        byte[] number501 = new byte[] {0, 0, 0x1, (byte) 0xf5};

        assertEquals(10, Constants.byteArrayToInt(number10));
        assertEquals(11, Constants.byteArrayToInt(number11));
        assertEquals(50, Constants.byteArrayToInt(number50));
        assertEquals(501, Constants.byteArrayToInt(number501));
    }

    @Test
    void intToByteArrayTest() {
        int number15 = 15;
        int number42 = 42;
        int number305 = 305;

        assertArrayEquals(new byte[]{0, 0, 0, 0xf}, Constants.intToByteArray(number15));
        assertArrayEquals(new byte[]{0, 0, 0, 0x2a}, Constants.intToByteArray(number42));
        assertArrayEquals(new byte[]{0, 0, 0x1, 0x31}, Constants.intToByteArray(number305));
    }
}