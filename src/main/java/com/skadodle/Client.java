package com.skadodle;

import java.io.*;
import java.net.*;

public class Client {
    private int port;
    private String ip;
    private String name;

    public Client(int port, String ip, String name) {
        this.port = port;
        this.ip = ip;
        this.name = name;
    }

    public void sendConnect() {}
    public byte[] receiveMap() {
        return new byte[2];
    }
    public void sendReady() {}
    public void receiveStart() {}
}
