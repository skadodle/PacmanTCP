package com.skadodle;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

import static com.skadodle.Constants.MAP_QUARTER_HEIGHT;
import static com.skadodle.Constants.MAP_QUARTER_WIDTH;

class MapTest {

    Map map = new Map();

    private byte mapValueOnIndex(int y, int x, byte[] array) {
        return array[y * MAP_QUARTER_WIDTH + x];
    }

    @Test
    void initializeFullMap() {
        var quarterMap = map.getMap();
        var fullMap = map.getFullMap();

        Random rand = new Random();
        int x;
        int y;

        for (int i = 0; i < 20; i++) {
            x = rand.nextInt(MAP_QUARTER_WIDTH);
            y = rand.nextInt(MAP_QUARTER_HEIGHT);
            assertEquals(mapValueOnIndex(y, x, quarterMap), fullMap[x][y]);
        }
    }
}