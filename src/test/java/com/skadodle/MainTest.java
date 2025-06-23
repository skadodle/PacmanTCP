package com.skadodle;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private static boolean isValidIp(String ip) {
        String regex = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }


    @Test
    void testValidIp() {
        assertTrue(isValidIp("192.168.1.1"));
        assertTrue(isValidIp("255.255.255.255"));
        assertTrue(isValidIp("0.0.0.0"));
        assertTrue(isValidIp("123.45.67.89"));
    }

    @Test
    void testInvalidIp() {
        assertFalse(isValidIp("256.0.0.1"));
        assertFalse(isValidIp("192.168.1"));
        assertFalse(isValidIp("192.168.1.1.1"));
        assertFalse(isValidIp("192.168.1.256"));
        assertFalse(isValidIp("abc.def.ghi.jkl"));
        assertFalse(isValidIp("256.100.100.100"));
    }
}