package ClientSide.login;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


/*------------------------------------------------------
 * 尤其注意import 应该不会用到awt但是和javafx两个有很多重合的地方
 * 所以fxml文件里报错可能是这边的controller引用了错误的包
 * -----------------------------------------------------*/

public class Page1 extends Application {

    private Stage primaryStage;

    @FXML
    private AnchorPane startUp;
    @FXML
    private Button login;
    @FXML
    private Button signUp;

    /*The first stage loader
     * 非必要不要动，免得碰坏了*/
    @FXML
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage=stage;
        //AnchorPane root = new AnchorPane();

        URL fxmlLocation = getClass().getResource("/Page1.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Page1.fxml"));
        fxmlLoader.setLocation(getClass().getResource("/Page1.fxml"));
        //fxmlLoader.setRoot(new AnchorPane());

        Parent root = fxmlLoader.load();  // 邮件中说的要改的第68行
        Page1 loginViewController = fxmlLoader.getController();
        primaryStage.setScene(new Scene(root));

        primaryStage.setTitle("Central-perk Chatroom");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("light_bulb.png"));
        primaryStage.setWidth(650);
        primaryStage.setHeight(505);
        primaryStage.show();
    }

    @FXML
    public void setLogin(ActionEvent event) throws IOException {
        URL fxmlLocation = getClass().getResource("/Page2.fxml");
        Parent parent= FXMLLoader.load(fxmlLocation);
        Scene pageCreateUser=new Scene(parent);

        Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(pageCreateUser);
        window.show();
    }

    @FXML
    public void setSignUp(ActionEvent event)throws IOException{
        //转场到PageCreateUser
        URL fxmlLocation = getClass().getResource("/Page3.fxml");
        Parent parent= FXMLLoader.load(fxmlLocation);
        Scene pageCreateUser=new Scene(parent);

        Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(pageCreateUser);
        window.show();
    }

    public static void main(String[] args) {launch(args);}
}

