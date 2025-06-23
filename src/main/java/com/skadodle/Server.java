package com.skadodle;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class Server {

    int frameTimeout = 200; // ms
    short port;
    int countOfPlayers;
    String serverName;
    ServerSocket serverSocket;
    Socket[] clientSockets;
    String[] playerNames;

    public Server(short port, int countOfPlayers, String serverName) {
        this.port = port;
        this.countOfPlayers = countOfPlayers;
        playerNames = new String[countOfPlayers - 1];
        this.serverName = serverName;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        clientSockets = new Socket[countOfPlayers - 1];
    }

    public void acceptClients() {
        for (int i = 0; i < countOfPlayers - 1; i++) {
            Packet packet = new Packet();
            try {
                clientSockets[i] = serverSocket.accept();
                packet.receivePacket(clientSockets[i], Constants.PACKET_TYPE_CONNECT);
                System.out.println(packet);
                playerNames[i] = new String(packet.getData());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Accepted Clients:\n" + Arrays.toString(playerNames));
    }

    public void sendMap(Map map) {
        Packet packet = new Packet(Constants.PACKET_TYPE_MAP);
        packet.setData(map.getMap());
        for (Socket socket : clientSockets) {
            try {
                packet.sendPacket(socket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(packet);
        System.out.println("Send Map!");
    }

    public void receiveReadyFromClients() {
        for (Socket socket : clientSockets) {
            Packet packet = new Packet();
            try {
                packet.receivePacket(socket, Constants.PACKET_TYPE_READY);
                System.out.println(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Received Ready from Clients!");
    }

    public Game sendStart(int startX, int startY) {
        Player[] players = new Player[countOfPlayers];

        players[0] = new Player(startX, startY, Constants.RIGHT, serverName.length(), serverName);

        int currentY = startY;

        for (int i = 1; i < countOfPlayers; i++) {
            if (i == 2)
                currentY = Constants.MAP_FULL_HEIGHT - 1 - startY;
            if (i % 2 == 1)
                players[i] = new Player(Constants.MAP_FULL_WIDTH - 1 - startX, currentY,
                        Constants.LEFT, playerNames[i - 1].length(), playerNames[i - 1]);
            else
                players[i] = new Player(startX, currentY, Constants.RIGHT,
                        playerNames[i - 1].length(), playerNames[i - 1]);
        }

        int sizePlayers = 0;
        for (Player player : players) {
            sizePlayers += player.getByteSize();
            System.out.println(player);
        }

        byte[] data = new byte[4 + 4 + sizePlayers];
        int pos = 0;
        System.arraycopy(Constants.intToByteArray(frameTimeout), 0, data, pos, 4);
        pos += 4;
        System.arraycopy(Constants.intToByteArray(countOfPlayers), 0, data, pos, 4);
        pos += 4;

        for (Player player : players) {
            System.arraycopy(player.convertToBytes(), 0, data,
                    pos, player.getByteSize());
            pos += player.getByteSize();
        }


        Packet packet = new Packet(Constants.PACKET_TYPE_START);
        packet.setData(data);
        System.out.println(packet);

        for (Socket socket : clientSockets) {
            try {
                packet.sendPacket(socket);
            } catch(IOException e){
                throw new RuntimeException(e);
            }
        }

        return new Game(frameTimeout, countOfPlayers, players);
    }
}
