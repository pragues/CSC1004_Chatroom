package ClientSide.login;

import ClientSide.chatwindow.LoggedInChatbox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;


/*This class implement the clients who already got an acount.
* In GUI and database implementation*/
public class Page2 {
    //用static修饰的成员变量和方法从属于class，没有static关键词的属于成员变量

    @FXML
    private Button Login;
    @FXML
    private TextField userName;
    @FXML
    private TextField passWord;
    private ResultSet resultSet;  //java.sql
    private static String username;
    private static String password;


    @FXML
    public void getUsername(ActionEvent actionEvent) {username=userName.getText();}
    @FXML
    public void getPassword(ActionEvent actionEvent) {password=passWord.getText();}
    
    public static String giveUsername(){return username;}
    public static String givePassword(){return password;}

    @FXML
    public void setLogin(ActionEvent event)throws IOException{
        //转场到PageCreateUser

        getUsername(event);
        getPassword(event);

        JDBC jdbc2= new JDBC();
        jdbc2.initConnection();
        boolean checkUser= jdbc2.searchForId(username);

        if (checkUser==false){
            System.out.println("The user does not exist. ");
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("USERNAME DO NOT EXIST");
            alert.setContentText("Please check the username. ");
            alert.show();
        }else {
            boolean go= jdbc2.confirmForPassword(username,password);
            jdbc2.closeConnection();
            if (go==true){

//                //Start the server first while the info is right
//                ServerSocket serverSocket1 = new ServerSocket(1233);
//                //The server object of this class
//                Server server =new Server(serverSocket1);
//                server.startServer();
                //TODO
                //目前server都没有close

                System.out.println("USER: "+username +";  PASSWORD: "+ password+ ";  has logged in successfully"+"（来自Page2）");

                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("LOGIN! ");
                alert.setHeaderText("Login successfully!");
                alert.setContentText("Please click to continue! ");
                alert.show();

                URL fxmlLocation = getClass().getResource("/Chatbox.fxml");
                Parent parent= FXMLLoader.load(fxmlLocation);
                Scene pageCreateUser=new Scene(parent);

                Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(pageCreateUser);
                window.setHeight(702.0);
                window.setWidth(847.0);
                window.show();


            }else {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setWidth(400);
                alert.setTitle("ERROR");
                alert.setHeaderText("PASSWORD WRONG");
                alert.setContentText("Please check the password.");
                alert.show();

                System.out.println("The password does not match with the username. Please check the password.");
            }
        }
        System.out.println(checkUser);
    }

}