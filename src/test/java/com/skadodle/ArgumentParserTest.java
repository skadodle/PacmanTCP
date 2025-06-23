package com.skadodle;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentParserTest {
    private static boolean validateIp(String ip) {
        String regex = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    private static boolean validatePort(int port) {
        return !(port < 1000 || port > 65535);
    }

    private static boolean validateCountOfPlayers(int count) {
        return !(count < 2 || count > 4);
    }


    @Test
    void testValidIp() {
        assertTrue(validateIp("192.168.1.1"));
        assertTrue(validateIp("255.255.255.255"));
        assertTrue(validateIp("0.0.0.0"));
        assertTrue(validateIp("123.45.67.89"));
    }

    @Test
    void testValidPort() {
        assertTrue(validatePort(65530));
        assertTrue(validatePort(65531));
        assertTrue(validatePort(14145));
        assertTrue(validatePort(14045));
        assertTrue(validatePort(14325));
    }

    @Test
    void testValidCountOfPlayers() {
        assertTrue(validateCountOfPlayers(4));
        assertTrue(validateCountOfPlayers(3));
        assertTrue(validateCountOfPlayers(2));
    }

    @Test
    void testInvalidIp() {
        assertFalse(validateIp("256.0.0.1"));
        assertFalse(validateIp("192.168.1"));
        assertFalse(validateIp("192.168.1.1.1"));
        assertFalse(validateIp("192.168.1.256"));
        assertFalse(validateIp("abc.def.ghi.jkl"));
        assertFalse(validateIp("256.100.100.100"));
    }

    @Test
    void testInvalidPort() {
        assertFalse(validatePort(0));
        assertFalse(validatePort(65536));
        assertFalse(validatePort(65537));
        assertFalse(validatePort(-1005));
        assertFalse(validatePort(-1022));
    }

    @Test
    void testInvalidCountOfPlayers() {
        assertFalse(validateCountOfPlayers(1));
        assertFalse(validateCountOfPlayers(0));
        assertFalse(validateCountOfPlayers(-5));
        assertFalse(validateCountOfPlayers(15));
    }
}