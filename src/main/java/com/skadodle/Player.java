package com.skadodle;

import java.util.Arrays;

public class Player {
    private int positionX;
    private int positionY;
    private int direction;
    private final int playerNameLength;
    private final String playerName;

    Player(int positionX, int positionY, int direction, int playerNameLength, String playerName) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.direction = direction;
        this.playerNameLength = playerNameLength;
        this.playerName = playerName;
    }

    public byte[] convertToBytes() {
        byte[] data = new byte[4 + 4 + 4 + 4 + playerNameLength];

        int pos = 0;

        System.arraycopy(Constants.intToByteArray(positionX), 0, data, pos, 4);
        pos += 4;

        System.arraycopy(Constants.intToByteArray(positionY), 0, data, pos, 4);
        pos += 4;

        System.arraycopy(Constants.intToByteArray(direction), 0, data, pos, 4);
        pos += 4;

        System.arraycopy(Constants.intToByteArray(playerNameLength), 0, data, pos, 4);
        pos += 4;

        byte[] playerNameByte = playerName.getBytes();
        System.arraycopy(playerNameByte, 0, data, pos, playerNameLength);

        return data;
    }

    public static Player[] convertFromBytes(byte[] bytes) {
        int pos = 0;
        pos += 4;   // Skip frameTimeout
        int countOfPlayers = Constants.byteArrayToInt(Arrays.copyOfRange(bytes, pos, pos + 4));
        pos += 4;

        Player[] players = new Player[countOfPlayers];

        for (int i = 0; i < countOfPlayers; i++) {
            int currentStartX = Constants.byteArrayToInt(Arrays.copyOfRange(bytes, pos, pos + 4));
            pos += 4;
            int currentStartY = Constants.byteArrayToInt(Arrays.copyOfRange(bytes, pos, pos + 4));
            pos += 4;
            int currentDirection = Constants.byteArrayToInt(Arrays.copyOfRange(bytes, pos, pos + 4));
            pos += 4;
            int currentPlayerNameLength = Constants.byteArrayToInt(Arrays.copyOfRange(bytes, pos, pos + 4));
            pos += 4;

            System.out.println("currentStartX: " + currentStartX);
            System.out.println("currentStartY: " + currentStartY);
            System.out.println("currentDirection: " + currentDirection);
            System.out.println("currentPlayerNameLength: " + currentPlayerNameLength);

            String currentPlayerName = new String(bytes, pos, currentPlayerNameLength);
            players[i] = new Player(currentStartX, currentStartY, currentDirection, currentPlayerNameLength, currentPlayerName);
            pos += currentPlayerNameLength;
            System.out.println("currentPlayerName: " + currentPlayerName);

            System.out.println("-".repeat(150));
        }

        return players;
    }

    public int getByteSize() {
        return 4 + 4 + 4 + 4 + playerNameLength;
    }

    public int getPositionX() {
        return positionX;
    }
    public int getPositionY() {
        return positionY;
    }
    public int getDirection() {
        return direction;
    }
    public int getPlayerNameLength() {
        return playerNameLength;
    }
    public String getPlayerName() {
        return playerName;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "(" + positionX + ", " + positionY + ", " + direction + ", " +
                playerNameLength + ", " + playerName + ")";
    }
}
