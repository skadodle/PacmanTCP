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

    public void startGame(Map map) {
        System.out.println("Starting game...");
    }
}
