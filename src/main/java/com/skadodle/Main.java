package com.skadodle;

import com.skadodle.ArgumentParser.ArgumentParser;
import com.skadodle.ArgumentParser.Arguments;

public class Main {
    public static void main(String[] args) {
        System.out.println("-".repeat(100));
        int port = -1;
        String ip = null;
        String name = null;
        int countOfPlayers = 0;

        boolean isServer = false;

        try {
            Arguments arguments = ArgumentParser.parse(args);

            try {
                arguments.validateArguments();
            }
            catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }

            // Set variables
            port = arguments.getPort();
            ip = arguments.getIp();
            name = arguments.getName();
            countOfPlayers = arguments.getCountOfPlayers();

            if (ip == null)
                isServer = true;
        } catch (IllegalArgumentException e) {
            System.err.println("Error parsing arguments: " + e.getMessage());
            System.err.println("Usage: java Main -i <ip> -p <port> -c <count> -n <name>");
            System.exit(1);
        }

        System.out.println("Port: " + port);
        System.out.println("Ip: " + ip);
        System.out.println("Count: " + countOfPlayers);
        System.out.println("Name: " + name);
        System.out.println("Server or Client: " + (isServer ? "Server" : "Client"));

        System.out.println("-".repeat(100));


    }
}