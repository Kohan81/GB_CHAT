package com.example.gb_chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

public class Network {
    public static final String SERVER_HOST = "127.0.0.1";
    public static final int SERVER_PORT = 8189;

    private String host;
    private int port;
    private Socket socket;

    DataInputStream socketInput;
    DataOutputStream socketOutput;

    public Network(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Network() {
        this(SERVER_HOST, SERVER_PORT);
    }

    public boolean connect(){
        try {
            socket = new Socket(host, port);
            socketInput = new DataInputStream(socket.getInputStream());
            socketOutput = new DataOutputStream(socket.getOutputStream());
            return true;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public void sendMessage(String message) throws IOException {
        try {
            socketOutput.writeUTF(message);
        } catch (IOException e) {
            System.err.println("failed to SEND message to server");
            throw e;
        }
    }

    public void waitMessadges(Consumer<String> messageHandler){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String messsage = socketInput.readUTF();
                        messageHandler.accept(messsage);
                    } catch (IOException e) {
                        System.err.println("failed to GET message from server");
                        break;
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void closeSocket(){
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("failed to close network connection");
        }
    }
}
