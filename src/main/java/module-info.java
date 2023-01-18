module com.example.gb_chat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gb_chat to javafx.fxml;
    exports com.example.gb_chat;
}