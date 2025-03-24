package com.skadodle;

public class Constants {
    // MAP
    public static final int MAP_QUARTER_HEIGHT = 15;
    public static final int MAP_QUARTER_WIDTH = 20;

    public static final int MAP_FULL_HEIGHT = MAP_QUARTER_HEIGHT * 2;
    public static final int MAP_FULL_WIDTH = MAP_QUARTER_WIDTH * 2;

    public static final byte WALL = (byte)0xFF;
    public static final byte DOT = (byte)0xAA;
    public static final byte PLAYER = (byte)0x22;

    public static final char WALLSYM = '#';
    public static final char DOTSYM = '*';
    public static final char PLAYERSYM = '$';

    // PACKET
    public final static byte []magic = {(byte)0xAB, (byte)0xCD, (byte)0xFE, (byte)0x01};

    public static final byte []pConnect = {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01};
    public static final byte []pMap = {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x10};
    public static final byte []pReady = {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x02};
    public static final byte []pStart = {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x20};
    public static final byte []pKey = {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
    public static final byte []pRedirectKey = {(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF};
}
