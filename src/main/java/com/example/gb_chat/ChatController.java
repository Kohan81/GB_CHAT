package com.example.gb_chat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.function.Consumer;

public class ChatController {
    @FXML
    public TextField textField;
    @FXML
    public Button button;
    @FXML
    public TextArea textArea;
    @FXML
    public ListView userList;

    private Network network;
    private ClientChat application;

    @FXML
    private void send_Message(){
        String message = textField.getText();

        textArea.appendText(DateFormat.getTimeInstance().format(new Date()) + " ");
        textField.requestFocus();
        appendMessageToChat(message);
        try {
            network.sendMessage(message);
        } catch (IOException e) {
            application.showErrorDialog("network communication error");
        }
    }
    @FXML
    public void appendMessageToChat(String message) {
        if (!message.isEmpty()) {
            textArea.appendText(message.trim());
            textArea.appendText(System.lineSeparator());
            textField.clear();
        }
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
        network.waitMessadges(new Consumer<String>() {
            @Override
            public void accept(String message) {
                appendMessageToChat(message);
            }
        });
    }

    public ClientChat getApplication() {
        return application;
    }

    public void setApplication(ClientChat application) {
        this.application = application;
    }
}