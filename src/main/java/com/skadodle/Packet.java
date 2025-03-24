package com.skadodle;

import java.util.Arrays;

import static com.skadodle.Constants.*;

public class Packet {
    private final byte[] magic = Constants.magic;
    private final byte[] packetType;
    private byte[] dataSize = new byte[4];
    private byte[] data = null;

    public Packet(byte packetType, byte []dataSize) {
        try {
            this.packetType = parsePacketType(packetType);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        this.dataSize = dataSize;
        this.data = new byte[getdataSize()];
    }

    public Packet(byte packetType) {
        try {
            this.packetType = parsePacketType(packetType);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private static int byteArrayToInt(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8) |
                ((bytes[3] & 0xFF));
    }
    private static byte[] intToByteArray(int value) {
        return new byte[] {
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value
        };
    }

    private byte[] parsePacketType(byte packetType) {
        byte[] returnValue = switch (packetType) {
            case (byte) 0x00 -> pKey;
            case (byte) 0x01 -> pConnect;
            case (byte) 0x02 -> pReady;
            case (byte) 0x10 -> pMap;
            case (byte) 0x20 -> pStart;
            case (byte) 0xFF -> pRedirectKey;
            default -> new byte[]{0x00, 0x00, 0x00, (byte) 0x05};
        };
        if (returnValue[3] == (byte)0x05) {
            throw new IllegalArgumentException("packetType is illegal!");
        }
        return returnValue;
    }
    
    public void setData(byte []data) {
        this.data = data;
    }

    public int getPacketType() {
        return byteArrayToInt(packetType);
    }

    public int getdataSize() {
        return byteArrayToInt(dataSize);
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return Arrays.toString(magic) + " " + Arrays.toString(packetType) + " " + Arrays.toString(dataSize) + " " + Arrays.toString(data);
    }
}
