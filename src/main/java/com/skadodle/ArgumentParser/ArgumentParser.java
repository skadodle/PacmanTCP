package com.skadodle.ArgumentParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgumentParser {
    public static Arguments parse(String[] args) throws IllegalArgumentException {
        Arguments arguments = new Arguments();

        try {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-i":
                        arguments.setIp(args[++i]);
                        validateIp(arguments.getIp());
                        break;
                    case "-p":
                        int port = Integer.parseInt(args[++i]);
                        validatePort(port);
                        arguments.setPort(port);
                        break;
                    case "-c":
                        int count = Integer.parseInt(args[++i]);
                        validateCountOfPlayers(count);
                        arguments.setCountOfPlayers(count);
                        break;
                    case "-n":
                        arguments.setName(args[++i]);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown option: " + args[i]);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Missing value for option: " + args[args.length - 1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        return arguments;
    }

    private static void validateIp(String ip) {
        String regex = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ip);
        if (!matcher.matches())
            throw new IllegalArgumentException("Invalid IP address");
    }

    private static void validatePort(int port) {
        if (port < 1000 || port > 65535) {
            throw new IllegalArgumentException("Port must be between 1000 and 65535");
        }
    }

    private static void validateCountOfPlayers(int count) {
        if (count < 2 || count > 4) {
            throw new IllegalArgumentException("Count of players must be between 2 and 4");
        }
    }
}
