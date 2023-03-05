package ClientSide.client.login;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Page2 extends Application {

    //public static void main(String[] args) {launch(args);}

    private PreparedStatement preparedStatement;
    private static final String USER_NAME="root";
    private static final String PASSWORD = "admin123456";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    //private static final String URL = "jdbc:mysql://localhost:3306/newsSystem?allowPublicKeyRetrieval=true&useSSL=false&characterEncoding=utf-8";

    @FXML
    private TextField usnInput;
    @FXML
    private TextField pswdInput;
    @FXML
    private Button cancelWindow;

    private ResultSet resultSet;  //java.sql
    @Override
    public void start(Stage primaryStage) {

    }
}
