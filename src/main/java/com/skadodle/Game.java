package com.skadodle;

public class Game {

    private final int frameTimeout;
    private final int countOfPlayers;
    private Player[] players;

    Game(int frameTimeout, int countOfPlayers,  Player[] players) {
        this.frameTimeout = frameTimeout;
        this.countOfPlayers = countOfPlayers;
        this.players = players;
    }

    public byte[] convertToBytes() {
        int playersSize = 0;
        for (Player player : players) {
            playersSize += player.getByteSize();
        }

        byte[] data = new byte[4 + 4 + playersSize];

        int pos = 0;
        System.arraycopy(Constants.intToByteArray(frameTimeout), 0, data, pos, 4);
        pos += 4;
        System.arraycopy(Constants.intToByteArray(countOfPlayers), 0, data, pos, 4);
        pos += 4;

        for (Player player : players) {
            int playerByteSize = player.getByteSize();
            System.arraycopy(player.convertToBytes(), 0, data, pos, playerByteSize);
            pos += playerByteSize;
        }

        return data;
    }
}
