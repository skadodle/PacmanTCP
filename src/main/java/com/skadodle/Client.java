package com.skadodle;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class Client {

    private final int port;
    private final String ip;
    private final String name;
    private Socket socket;

    public Client(int port, String ip, String name) {
        this.port = port;
        this.ip = ip;
        this.name = name;
    }

    private void connectToServer() {
        Socket socket;
        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.socket = socket;
    }

    public void sendConnect() {
        connectToServer();
        Packet packet = new Packet(Constants.PACKET_TYPE_CONNECT);
        packet.setData(name.getBytes());
        System.out.println(packet);
        try {
            packet.sendPacket(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] receiveMap() {
        Packet packet = new Packet();
        try {
            packet.receivePacket(socket, Constants.PACKET_TYPE_MAP);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(packet);
        return packet.getData();
    }

    public void sendReady() {
        Packet packet = new Packet(Constants.PACKET_TYPE_READY);
        packet.setDataSize(Constants.intToByteArray(0));
        try {
            packet.sendPacket(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(packet);
    }

    public Game receiveStart() {
        Packet packet = new Packet();
        try {
            packet.receivePacket(socket, Constants.PACKET_TYPE_START);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int pos = 0;
        int frameTimeout = Constants.byteArrayToInt(Arrays.copyOfRange(packet.getData(), pos, pos + 4));
        pos += 4;
        int countOfPlayers = Constants.byteArrayToInt(Arrays.copyOfRange(packet.getData(), pos, pos + 4));

        return new Game(frameTimeout, countOfPlayers, Player.convertFromBytes(packet.getData()));
    }
}
