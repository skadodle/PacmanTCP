package com.skadodle;

import java.util.Arrays;
import java.util.Random;

import static com.skadodle.Constants.*;

public class Map {
    // RANDOM
    private final Random rand = new Random();

    // VARIABLES
    private final int[] playerPos = new int[2];
    private byte []map = new byte[MAP_QUARTER_HEIGHT * MAP_QUARTER_WIDTH];
    private final byte [][]fullMap = new byte[MAP_FULL_WIDTH][MAP_FULL_HEIGHT];

    Map(byte []map) {
        this.map = map;
        initializeFullMap();
    }

    Map() {
        generateMap();
    }

    public void generateMap() {
        Arrays.fill(map, DOT);
        map[(rand.nextInt(MAP_QUARTER_HEIGHT - 2) + 1) * MAP_QUARTER_WIDTH +
                (rand.nextInt(MAP_QUARTER_WIDTH - 2) + 1)] = WALL;

        boolean flag = true;
        while (flag) {
            flag = false;
            for (int x = 1; x < MAP_QUARTER_WIDTH - 1; x++)
                for (int y = 1; y < MAP_QUARTER_HEIGHT - 1; y++)
                {
                    if (mapValueOnIndex(y, x) == WALL)
                        continue;

                    if (countNeighbors(y, x) == 1 && rand.nextInt(2) != 1)
                    {
                        flag = true;
                        map[y * MAP_QUARTER_WIDTH + x] = WALL;
                    }
                }
        }

        for (short l = 0; l < 2; l++) {
            for (short i = 1; i < MAP_QUARTER_HEIGHT - 1; i++) {
                for (short j = 1; j < MAP_QUARTER_WIDTH - 1; j++) {
                    if (mapValueOnIndex(i, j) == WALL && isSurroundedByDots(i, j)) {
                        setMapValue(i, j, DOT);
                        continue;
                    }

                    if (isDiagonallySurroundedByDots(i, j) && shouldBecomeWall(i, j)) {
                        if (rand.nextInt(2) != 0) {
                            setMapValue(i, j, WALL);
                        }
                    }
                }
            }
        }

        validateMap();
        placePlayerOnMap();
        initializeFullMap();
    }

    private byte mapValueOnIndex(int y, int x) {
        return map[y * MAP_QUARTER_WIDTH + x];
    }

    private void setMapValue(int y, int x, byte value) {
        map[y * MAP_QUARTER_WIDTH + x] = value;
    }

    private boolean isSurroundedByDots(int i, int j) {
        return mapValueOnIndex(i - 1, j) == DOT &&
                mapValueOnIndex(i + 1, j) == DOT &&
                mapValueOnIndex(i, j - 1) == DOT &&
                mapValueOnIndex(i, j + 1) == DOT;
    }

    private boolean isDiagonallySurroundedByDots(int i, int j) {
        return mapValueOnIndex(i + 1, j + 1) == DOT &&
                mapValueOnIndex(i + 1, j - 1) == DOT &&
                mapValueOnIndex(i - 1, j + 1) == DOT &&
                mapValueOnIndex(i - 1, j - 1) == DOT;
    }

    private boolean shouldBecomeWall(int i, int j) {
        return (mapValueOnIndex(i - 1, j) == WALL && mapValueOnIndex(i + 1, j) == DOT &&
                ((mapValueOnIndex(i, j + 1) == WALL && mapValueOnIndex(i, j - 1) == DOT) ||
                (mapValueOnIndex(i, j - 1) == WALL && mapValueOnIndex(i, j + 1) == DOT))) ||
                (mapValueOnIndex(i + 1, j) == WALL && mapValueOnIndex(i - 1, j) == DOT &&
                ((mapValueOnIndex(i, j + 1) == WALL && mapValueOnIndex(i, j - 1) == DOT) ||
                (mapValueOnIndex(i, j - 1) == WALL && mapValueOnIndex(i, j + 1) == DOT)));
    }

    private int countNeighbors(int y, int x) {
        return countNeighborsDiagonal(y, x) + countNeighborsSide(y, x);
    }

    private int countNeighborsDiagonal(int y, int x) {
        int countBlock = 0;
        if (mapValueOnIndex(y + 1, x + 1) == WALL)
            countBlock++;
        if (mapValueOnIndex(y - 1, x + 1) == WALL)
            countBlock++;
        if (mapValueOnIndex(y + 1, x - 1) == WALL)
            countBlock++;
        if (mapValueOnIndex(y - 1, x - 1) == WALL)
            countBlock++;
        return countBlock;
    }

    private int countNeighborsSide(int y, int x) {
        int countBlock = 0;
        if (mapValueOnIndex(y - 1, x) == WALL)
            countBlock++;
        if (mapValueOnIndex(y + 1, x) == WALL)
            countBlock++;
        if (mapValueOnIndex(y, x - 1) == WALL)
            countBlock++;
        if (mapValueOnIndex(y, x + 1) == WALL)
            countBlock++;
        return countBlock;
    }

    private void validateMap() {
        boolean flag = true;
        while (flag) {
            flag = false;
            for (int x = 1; x < MAP_QUARTER_WIDTH - 1; x++) {
                for (int y = 1; y < MAP_QUARTER_HEIGHT - 1; y++) {
                    if (mapValueOnIndex(y, x) == WALL)
                        continue;

                    if (countNeighbors(y, x) < 2) {
                        setMapValue(y, x, WALL);
                        flag = true;
                    }
                }
            }
        }
    }

    private void placePlayerOnMap() {
        int x;
        int y;

        do {
            x = rand.nextInt(MAP_QUARTER_WIDTH);
            y = rand.nextInt(MAP_QUARTER_HEIGHT);
        } while (mapValueOnIndex(y, x) == WALL);

        setMapValue(y, x, PLAYER);
        playerPos[0] = x;
        playerPos[1] = y;
    }

    private void initializeFullMap() {
        for (int i = 0; i < MAP_QUARTER_WIDTH; i++) {
            for (int j = 0; j < MAP_QUARTER_HEIGHT; j++) {
                byte value = mapValueOnIndex(j, i);
                fullMap[i][j] = value;
                fullMap[i][MAP_FULL_HEIGHT - j - 1] = value;
                fullMap[MAP_FULL_WIDTH - i - 1][j] = value;
                fullMap[MAP_FULL_WIDTH - i - 1][MAP_FULL_HEIGHT - j - 1] = value;
            }
        }
    }

    public byte[] getMap() {
        return map;
    }

    public byte[][] getFullMap() {
        return fullMap;
    }

    public int getPlayerPosX() {
        return playerPos[0];
    }

    public int getPlayerPosY() {
        return playerPos[1];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < MAP_FULL_WIDTH; i++) {
            for (int j = 0; j < MAP_FULL_HEIGHT; j++) {
                switch (fullMap[i][j]) {
                    case WALL: sb.append(WALL_SYM); break;
                    case DOT: sb.append(DOT_SYM); break;
                    case PLAYER: sb.append(PLAYER_SYM); break;
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}