package com.skadodle.ArgumentParser;

public class Arguments {
    private String ip = "0.0.0.0";
    private boolean isIpSet = false;

    private int port = -1;
    private boolean isPortSet = false;

    private int countOfPlayers = -1;
    private boolean isCountOfPlayersSet = false;

    private String name = "Slava";

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
        isIpSet = true;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
        isPortSet = true;
    }

    public int getCountOfPlayers() {
        return countOfPlayers;
    }

    public void setCountOfPlayers(int countOfPlayers) {
        this.countOfPlayers = countOfPlayers;
        isCountOfPlayersSet = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void validateArguments() {
        if (isIpSet && isCountOfPlayersSet) {
            throw new IllegalArgumentException("Ip and count of players cannot be set at the same time");
        }
        if (!isPortSet) {
            throw new IllegalArgumentException("Port must be set");
        }
        if (!isIpSet && !isCountOfPlayersSet) {
            throw new IllegalArgumentException("Ip or count of players must be set");
        }
    }

    @Override
    public String toString() {
        return "Arguments{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", countOfPlayers=" + countOfPlayers +
                ", name='" + name + '\'' +
                '}';
    }
}
