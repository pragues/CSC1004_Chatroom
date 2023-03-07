package ClientSide.client.login;

import ClientSide.client.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;


import javafx.stage.Stage;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/*------------------------------------------------------
* 尤其注意import 应该不会用到awt但是和javafx两个有很多重合的地方
* 所以fxml文件里报错可能是这边的controller引用了错误的包
* -----------------------------------------------------*/

public class Page1 implements Initializable {

   //public static void main(String[] args) {launch(args);}

//    @Override
//    public void start(Stage primaryStage) {}

    @FXML
    private Stage primaryStage;
    @FXML
    private Scene scene1;
    @FXML
    private AnchorPane startUp;
    @FXML
    private Button login;
    @FXML
    private Button signUp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //这个函数是干啥子的额？
    }
    /*设置转场是在button上添加event
    * 是不是在page i里面的scene添加getScene 方法就能在别的里面用了*/


    public void setLogin(ActionEvent event) throws IOException {
        //button转场到page2
        Parent parent= FXMLLoader.load(getClass().getResource("Page2.fxml"));
        Scene page2=new Scene(parent);

        Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(page2);
        window.show();

    }
    public void setSignUp(ActionEvent event)throws IOException{
        //转场到PageCreateUser
        Parent parent= FXMLLoader.load(getClass().getResource("PageCreateUser.fxml"));
        Scene pageCreateUser=new Scene(parent);

        Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(pageCreateUser);
        window.show();
    }
}

