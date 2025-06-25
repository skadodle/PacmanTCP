package com.skadodle;

import com.skadodle.ArgumentParser.ArgumentParser;
import com.skadodle.ArgumentParser.Arguments;

public class Main {
    static {
        System.setProperty("--module-path /Library/Java/JavaVirtualMachines/javafx-sdk-21.0.7/lib --add-modules=javafx.controls,javafx.fxml", "true");
    }


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
                System.err.flush();
                System.exit(1);
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
            System.err.println("./gradlew run --args=\"-i <validIp> -p <validPort> -n <validName> " +
                    "-c <countOfPlayers between 2 and 4>\"");
            System.err.flush();
            System.exit(1);
        }

        System.out.println("Port: " + port);
        System.out.println("Ip: " + ip);
        System.out.println("Count: " + countOfPlayers);
        System.out.println("Name: " + name);
        System.out.println("Server or Client: " + (isServer ? "Server" : "Client"));

        System.out.println("-".repeat(100));

        if (isServer) {
            Server server = new Server((short)port, countOfPlayers, name);
            Map map = new Map();
            server.acceptClients();
            server.sendMap(map);
            server.receiveReadyFromClients();
            Game game = server.sendStart(map.getPlayerPosX(), map.getPlayerPosY());
        } else {
            Client client = new Client((short)port, ip, name);
            client.sendConnect();
            Map map = new Map(client.receiveMap());
            client.sendReady();
            Game game = client.receiveStart();
        }
    }
}