package ClientSide.login;

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

/*This class implement the function of create an account for those who have not
* got an account  */
public class Page3 {

    @FXML
    private TextField newUser;
    @FXML
    private TextField newPassword;
    @FXML
    private javafx.scene.control.Button up;
    @FXML
    private Button backward;


    private String userName;
    private String passWord;

    public static void handleLogout(){
        //TODO
        //转场到getScene1
    };

    @FXML
    public void setBackward(ActionEvent actionEvent){
        URL fxmlLocation = getClass().getResource("/Page1.fxml");
        Parent parent= null;
        try {
            parent = FXMLLoader.load(fxmlLocation);
            Scene pageCreateUser=new Scene(parent);

            Stage window= (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(pageCreateUser);
            window.setHeight(462);
            window.setWidth(635);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

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
        try{
            Parent parent= FXMLLoader.load(Page3.class.getResource("../../../../resources/Page1.fxml"));
            Scene chatbox=new Scene(parent);
            Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(chatbox);
            window.show();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    public void signUping(ActionEvent event)throws IOException{
        //转场到PageCreateUser
        setNewUser(event);
        setNewPassword(event);
        JDBC jdbc3= new JDBC();
        jdbc3.initConnection();

        //如果用户名和密码都重复了就会会报错，但是有一个错了是正常的
        boolean haveOne= jdbc3.confirmForPassword(userName,passWord);
        if (haveOne){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("USER exists.");
            alert.setContentText("Please change your username or password. ");
            alert.show();
        }else {
            jdbc3.add(userName,passWord);

            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("CREATED!");
            alert.setHeaderText("Created successfully!");
            alert.setContentText("Please login to continue! ");
            alert.show();

            URL fxmlLocation = getClass().getResource("/Page1.fxml");
            Parent parent= FXMLLoader.load(fxmlLocation);
            Scene pageCreateUser=new Scene(parent);

            Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(pageCreateUser);
            window.show();
        }
        jdbc3.closeConnection();

    }

    @FXML
    public void setNewPassword(ActionEvent event) {
        passWord=newPassword.getText();
    }
    @FXML
    public void setNewUser(ActionEvent event) {
        userName=newUser.getText();
    }


}
