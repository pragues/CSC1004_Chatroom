package ClientSide.client.login;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.*;

/*This class implement the function of create an account for those who have not
* got an account  */
public class PageCreateUser{

    //public static void main(String[] args) {launch(args);}
//    @Override
//    public void start(Stage primaryStage) {
//
//    }

    private TextField newUser;
    private TextField newPassword;

    private Button cancelOperation;
    private Scene scenePageCreateUser;
    private static Main mainApp;

    public Main getMainApp() {return mainApp;}

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    /*供外部转场调用*/
    public Scene getScenePageCreateUser() {return scenePageCreateUser;}

    public void SignUp() throws Exception{

    }


    /**
     * 用户退出，返回登录界面
     * user quits, back to login page
     */
    @FXML
//    private static void handleLogout() {
//        try {
//            //TODO
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(Main.class.getResource("views/LoginView.fxml"));
//            Parent loginView = loader.load();
//
//            //LoginViewController loginViewController = loader.getController();
//            Page1 page1 =loader.getController();
//
//            //mainApp.getPrimaryStage().setScene(new Scene(loginView, 1152, 640));
//            mainApp.getPrimaryStage().show();
//            page1.setMainApp(mainApp);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void handleLogout(){
        //TODO
        //转场到getScene1
    };

    public void setNewUser(TextField username){

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
                //返回登录界面然后输入新的用户名密码再登录
                //TODO
                handleLogout();
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











