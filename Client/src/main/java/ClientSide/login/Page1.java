package ClientSide.login;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import javafx.util.Duration;

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
    @FXML
    private ImageView gingerMan1;
    @FXML
    private ImageView gingerMan2;
    @FXML
    private ImageView sayHi;
    @FXML
    private ImageView leftFlower;
    @FXML
    private ImageView middleFlower;
    @FXML
    private ImageView rightFlower;


    /*The first stage loader
     * 非必要不要动，免得碰坏了*/

    @FXML
    private void initialize(){

        //设置背景好看的的图片
        Image ginger1= new Image("PagePicture/Ginger慢-removebg-preview.png");
        gingerMan1.setImage(ginger1);

        Image ginger2= new Image("PagePicture/anotherGinger-removebg-preview.png");
        gingerMan2.setImage(ginger2);

        Image bubble= new Image("PagePicture/下载-removebg-preview.png");
        sayHi.setImage(bubble);

        Image leftImage= new Image("PagePicture/images-removebg-preview.png");
        leftFlower.setImage(leftImage);

        Image midImage= new Image("PagePicture/QQ图片20230423234722-removebg-preview.png");
        middleFlower.setImage(midImage);

        Image rightImage= new Image("PagePicture/3057c4ec8d2d125-removebg-preview.png");
        rightFlower.setImage(rightImage);

        gingerMan2.setRotate(-30);

        RotateTransition rotateTransition= new RotateTransition(Duration.millis(1000.0), gingerMan2);
        rotateTransition.setFromAngle(0);
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        rotateTransition.setByAngle(40);
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.setAutoReverse(true);
        rotateTransition.play();

        RotateTransition rotateTransition1= new RotateTransition(Duration.millis(1000.0), gingerMan1);
        rotateTransition1.setDelay(Duration.millis(300));
        rotateTransition1.setFromAngle(0);
        rotateTransition1.setCycleCount(Animation.INDEFINITE);
        rotateTransition1.setByAngle(-35);
        rotateTransition1.setInterpolator(Interpolator.LINEAR);
        rotateTransition1.setAutoReverse(true);
        rotateTransition1.play();

    }
    @FXML
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage=stage;

        URL fxmlLocation = getClass().getResource("/Page1.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

        fxmlLoader.setLocation(getClass().getResource("/Page1.fxml"));

        Parent root = fxmlLoader.load();  // 邮件中说的要改的第68行
        Page1 loginViewController = fxmlLoader.getController();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Central-perk Chatroom");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("PagePicture/light_bulb.png"));
        primaryStage.setWidth(635);
        primaryStage.setHeight(462);
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

