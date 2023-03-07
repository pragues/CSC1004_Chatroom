package ClientSide.client.login;

import ClientSide.client.Main;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Page1 implements Initializable {

   //public static void main(String[] args) {launch(args);}

//    public Stage primaryStage;
//
//    public BorderPane bp1;
//
//    public StackPane sp1;
//    @Override
//    public void start(Stage primaryStage) {
//
//    }

    @FXML
    private Stage primaryStage;
    @FXML
    private Scene scene1;
    @FXML
    private Scene scene2;
    @FXML
    private Scene stage3;
    @FXML
    private AnchorPane startUp;
    @FXML
    private Button login;
    @FXML
    private Button signUp;
    private Main mainApp;



    public void setMainApp(Main mainApp){this.mainApp=mainApp;}
    public Main getMainApp(){return mainApp;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public static void signUpUser(ActionEvent event, String username, String password){
        Connection connection=null;
        PreparedStatement psInsert= null;
        PreparedStatement psCheckUserExist=null;
        ResultSet resultSet=null;

        try{
            //csc1004_chatroom_dadabase@localhost
            //jdbc:mysql://localhost:3306/csc1004_chatroom_dadabase

            //SELECT `users-id` FROM `csc1004_chatroom_dadabase`.`users-data`;
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/csc1004_chatroom_dadabase","root","");
            psCheckUserExist=connection.prepareStatement("SELECT * FROM username WHERE username=? ");
            //这里等号后面有几个问号就是下面有几个
            psCheckUserExist.setString(1,username);
            resultSet =psCheckUserExist.executeQuery();

            //psCheckUserExist.executeUpdate();
            if (resultSet.isBeforeFirst()){
                System.out.println(" This USERNAME already exists! ");
                Alert alert =new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Enter another USERNAME. ");
                alert.show();
            }else {
                //insert the new username and password into the dadabase
                psInsert= connection.prepareStatement("INSERT INTO users-data(username, password VALUES (?,?))");
                psInsert.setString(1,username);
                psInsert.setString(2,password);
                psInsert.executeUpdate();
                //登录到LoggedInChatbox 的界面
                //TODO
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            //We should always close database connection and free database resource
            //Close everything in the end
            if (resultSet!=null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psInsert!=null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psCheckUserExist!=null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection!=null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }


    }

}
