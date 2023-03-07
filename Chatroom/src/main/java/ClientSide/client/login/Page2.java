package ClientSide.client.login;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.awt.*;
import java.sql.*;


/*This class implement the clients who already got an acount.
* In GUI and database implementation*/
public class Page2 {

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
    private Button cancelOperation;

    private ResultSet resultSet;  //java.sql


    private void Login () throws Exception{

    }

    public static void loginUser(ActionEvent event, String username, String password){
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
