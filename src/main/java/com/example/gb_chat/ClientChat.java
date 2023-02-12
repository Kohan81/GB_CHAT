package com.example.gb_chat;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.example.gb_chat.Network.SERVER_HOST;
import static com.example.gb_chat.Network.SERVER_PORT;

public class ClientChat extends Application {

    private Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ClientChat.class.getResource("hello-view.fxml"));

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        this.stage.setTitle("Java FX Application");
        this.stage.setScene(scene);

        ChatController controller = fxmlLoader.getController();
        controller.userList.getItems().addAll("user_1", "user_2");

        stage.show();
        connectToServer(controller);
    }

    private void connectToServer(ChatController chatController) {

            Network network = new Network();
            boolean resultConnectServer = network.connect();
            if (!resultConnectServer){
                String errorMessage = "IOException";
                System.err.println(errorMessage);
                showErrorDialog(errorMessage);
            }

            chatController.setNetwork(network);
            chatController.setApplication(this);
        stage.setOnCloseRequest(new  EventHandler<WindowEvent>(){
            @Override
            public void handle(WindowEvent windowEvent) {
                network.closeSocket();
            }
        } );
    }

    public void showErrorDialog(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("EXCEPTION!!!");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}