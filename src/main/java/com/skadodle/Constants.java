package com.skadodle;

import java.nio.ByteBuffer;

public class Constants {
    // MAP
    public static final int MAP_QUARTER_HEIGHT = 15;
    public static final int MAP_QUARTER_WIDTH = 20;

    public static final int MAP_FULL_HEIGHT = MAP_QUARTER_HEIGHT * 2;
    public static final int MAP_FULL_WIDTH = MAP_QUARTER_WIDTH * 2;

    public static final byte WALL = (byte)0xFF;
    public static final byte DOT = (byte)0xAA;
    public static final byte PLAYER = (byte)0x22;

    public static final char WALL_SYM = '#';
    public static final char DOT_SYM = '*';
    public static final char PLAYER_SYM = '$';

    // KEYS
    public static final byte UP = 0;
    public static final byte RIGHT = 1;
    public static final byte DOWN = 2;
    public static final byte LEFT = 3;

    // PACKET
    public final static byte [] MAGIC = {(byte)0xAB, (byte)0xCD, (byte)0xFE, (byte)0x01};

    public static final byte [] PACKET_TYPE_CONNECT = {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01};
    public static final byte [] PACKET_TYPE_MAP = {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x10};
    public static final byte [] PACKET_TYPE_READY = {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x02};
    public static final byte [] PACKET_TYPE_START = {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x20};
    public static final byte [] PACKET_TYPE_KEY = {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
    public static final byte [] PACKET_TYPE_REDIRECT_KEY = {(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF};


    public static int byteArrayToInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }
    public static byte[] intToByteArray(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }
}
