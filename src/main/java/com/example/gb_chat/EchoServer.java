package com.example.gb_chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    private static final int PORT = 8189;

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(PORT)){

            System.out.println("Server started, waiting for connection");
            Socket clientSocket = serverSocket.accept();
            System.out.println("client has been connected");

            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());

            processClientConnection(inputStream, outputStream);

        }catch (IOException e){
            System.err.println("Exception on the PORT");
            e.printStackTrace();
        }
    }

    private static void processClientConnection(DataInputStream inputStream, DataOutputStream outputStream){

        while (true){
            try {
                String message = inputStream.readUTF();
                if (message.startsWith("/end")) {
                    break;
                }
                outputStream.writeUTF("ECHO: " + message);
            }catch (IOException e){
                System.err.println("network connection was FAILED closed!");
                break;
            }
        }
    }
}
