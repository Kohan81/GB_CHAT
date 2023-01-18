package com.example.gb_chat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.text.DateFormat;
import java.util.Date;

public class ChatController {
    @FXML
    public TextField messageField;
    @FXML
    public Button sendMessageButton;
    @FXML
    public TextArea messageTExtArea;
    @FXML
    public ListView userList;

    @FXML
    public void appendMessageToChat(ActionEvent actionEvent) {
        if (!messageField.getText().isEmpty()) {
            messageTExtArea.appendText(DateFormat.getDateInstance().format(new Date()));
            messageTExtArea.appendText(System.lineSeparator());
            if (!userList.getSelectionModel().isEmpty()) {
                messageTExtArea.appendText(userList.getSelectionModel().getSelectedItems().toString());
            }
            messageTExtArea.appendText(messageField.getText().trim());
            messageTExtArea.appendText(System.lineSeparator());
            messageTExtArea.appendText(System.lineSeparator());
            messageField.setFocusTraversable(true);
            messageField.clear();
            messageField.requestFocus();
        }
    }
}