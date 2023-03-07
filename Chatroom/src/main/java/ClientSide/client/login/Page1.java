package ClientSide.client.login;

import ClientSide.client.Main;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import javafx.stage.Stage;


import java.beans.EventHandler;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


/*------------------------------------------------------
* 尤其注意import 应该不会用到awt但是和javafx两个有很多重合的地方
* 所以fxml文件里报错可能是这边的controller引用了错误的包*/

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

    /*设置转场是在button上添加event
    * 是不是在page i里面的scene添加getScene 方法就能在别的里面用了*/


    public void setLogin(ActionEvent event){
        //button转场！
        Page2 page2=new Page2();

    }
    public void setSignUp(ActionEvent event){
        PageCreateUser pageCreateUser=new PageCreateUser();
        Scene next= pageCreateUser.getScene();
    }
}
