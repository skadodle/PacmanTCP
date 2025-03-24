package com.skadodle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

class PacketTest {

    private static int byteArrayToInt(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8) |
                ((bytes[3] & 0xFF));
    }
    private static byte[] intToByteArray(int value) {
        return new byte[] {
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value
        };
    }

    @Test
    void testByteArrayToInt() {
        byte[] arr = new byte[] {0x0, 0x0, 0x4, 0x0};
        assertEquals(0x400, byteArrayToInt(arr));
        System.out.println(byteArrayToInt(arr));
    }

    @Test
    void testIntToByteArray() {
        int num = 0x552;
        assertArrayEquals(new byte[]{0x0, 0x0, 0x5, 0x52}, intToByteArray(num));
        System.out.println(Arrays.toString(intToByteArray(num)));
    }
}