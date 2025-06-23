package com.skadodle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import static com.skadodle.Constants.*;

public class Packet {

    private byte[] magic = new byte[4];
    private byte[] packetType = new byte[4];
    private byte[] dataSize = new byte[4];
    private byte[] data = null;

    public Packet(byte[] packetType) {
        this.magic = MAGIC;
        this.packetType = packetType;
    }

    public Packet() {}


    public void setDataSize(byte[] dataSize) {
        this.dataSize = dataSize;
        this.data = new byte[byteArrayToInt(dataSize)];
    }

    public void setData(byte []data) {
        this.dataSize = intToByteArray(data.length);
        this.data = data;
    }

    public int getDataSize() {
        return byteArrayToInt(dataSize);
    }

    public byte[] getData() {
        return data;
    }


    public void sendPacket(Socket socket) throws IOException {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        byte[] header = new byte[12];
        for (int i = 0; i < 4; i++) {
            header[i] = magic[i];
            header[i + 4] = packetType[i];
            header[i + 8] = dataSize[i];
        }
        out.write(header);
        if (this.getDataSize() != 0) {
            out.write(this.data);
        }
        out.flush();
    }

    public void receivePacket(Socket socket, byte[] packetType) throws IOException {
        DataInputStream in = new DataInputStream(socket.getInputStream());
        in.readFully(this.magic);
        if (!Arrays.equals(this.magic, MAGIC)) {
            throw new IllegalArgumentException("magic is illegal!");
        }

        in.readFully(this.packetType);
        if (!Arrays.equals(this.packetType, packetType)) {
            throw new IllegalArgumentException("packetType is illegal!");
        }

        in.readFully(this.dataSize);
        this.data = new byte[byteArrayToInt(this.dataSize)];

        if (byteArrayToInt(this.dataSize) != 0) {
            int dataSize = in.read(this.data);
            if (dataSize != byteArrayToInt(this.dataSize)) {
                throw new IllegalArgumentException("packet data size does not match!");
            }
        }
    }


    @Override
    public String toString() {
        return "-".repeat(100) + "\n" + bytesToHex(magic) + "\n" + bytesToHex(packetType) + "\n" +
                bytesToHex(dataSize) + "\n" + bytesToHex(data) + "\n" + "-".repeat(100);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString().trim(); // удалить последний пробел
    }
}
