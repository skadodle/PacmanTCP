package com.skadodle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void convertToBytes() {
        Player player = new Player(100, 200, 90, 4, "John");
        byte[] expectedBytes = new byte[4 + 4 + 4 + 4 + 4];

        int pos = 0;
        System.arraycopy(Constants.intToByteArray(100), 0, expectedBytes, pos, 4);
        pos += 4;
        System.arraycopy(Constants.intToByteArray(200), 0, expectedBytes, pos, 4);
        pos += 4;
        System.arraycopy(Constants.intToByteArray(90), 0, expectedBytes, pos, 4);
        pos += 4;
        System.arraycopy(Constants.intToByteArray(4), 0, expectedBytes, pos, 4);
        pos += 4;
        System.arraycopy("John".getBytes(), 0, expectedBytes, pos, 4);

        assertArrayEquals(expectedBytes, player.convertToBytes());
    }

    @Test
    void convertFromBytes() {
        int frameTimeout = 999; // dummy
        int playerCount = 1;
        int pos = 0;

        Player player = new Player(100, 200, 90, 4, "John");
        byte[] playerBytes = player.convertToBytes();

        byte[] total = new byte[4 + 4 + playerBytes.length]; // frameTimeout + count + player
        System.arraycopy(Constants.intToByteArray(frameTimeout), 0, total, pos, 4);
        pos += 4;
        System.arraycopy(Constants.intToByteArray(playerCount), 0, total, pos, 4);
        pos += 4;
        System.arraycopy(playerBytes, 0, total, pos, playerBytes.length);

        Player[] result = Player.convertFromBytes(total);

        assertEquals(1, result.length);
        assertEquals(100, result[0].getPositionX());
        assertEquals(200, result[0].getPositionY());
        assertEquals(90, result[0].getDirection());
        assertEquals(4, result[0].getPlayerNameLength());
        assertEquals("John", result[0].getPlayerName());
    }
}