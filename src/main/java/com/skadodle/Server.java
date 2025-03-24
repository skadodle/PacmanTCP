package com.skadodle;

import java.io.*;
import java.net.*;

public class Server {

    public Server(short port) {
        try (
            Socket socket = new Socket("0.0.0.0", port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Enter name: ");
            final String name = userInput.readLine();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
