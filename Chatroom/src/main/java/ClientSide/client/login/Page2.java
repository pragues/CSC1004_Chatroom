package ClientSide.client.login;

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
import java.sql.*;


/*This class implement the clients who already got an acount.
* In GUI and database implementation*/
public class Page2 {

    //public static void main(String[] args) {launch(args);}

    @FXML
    private Button Login;
    @FXML
    private TextField userName;
    @FXML
    private TextField passWord;
    private ResultSet resultSet;  //java.sql
    private String username;
    private String password;


//    private void Login () throws Exception{
//
//    }
    @FXML
    public void getUsername(ActionEvent actionEvent) {username=userName.getText();}
    @FXML
    public void getPassword(ActionEvent actionEvent) {password=passWord.getText();}

    /*实现了转场和登录到数据库*/
    @FXML
    public void loginUser (ActionEvent event){
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
            if (!resultSet.isBeforeFirst()){
                System.out.println(" USERNAME not found in database ! ");
                Alert alert =new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Enter your USERNAME again. ");
                alert.show();
            }else {
                while (resultSet.next()){
                    String retrievedPassword= resultSet.getString("password");
                    if (retrievedPassword.equals(password)){

                        //这里match成功之后需要连接socket

                        try{
                            //TODO
                            //chatbox might be null?
                            Parent parent= FXMLLoader.load(getClass().getResource("/Chatbox.fxml"));
                            Scene chatbox=new Scene(parent);
                            Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
                            window.setScene(chatbox);
                            window.show();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }else {
                        System.out.println("Password does not match.");
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The provided password is not correct. ");
                    }
                }

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
