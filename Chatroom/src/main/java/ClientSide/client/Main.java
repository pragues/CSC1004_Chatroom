package ClientSide.client;


import ClientSide.client.login.Page1;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.util.Optional;


//整个client side 运行的主函数
// Tell the order of runnable program
//Event Handler for the event management

public class Main extends Application {

    public static void main(String[] args) {launch(args);}

    private Stage stage;

    private Scene scene;

    private Parent root;


    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) {
        //connect the socket and connect to the server

        //page1
        //page2 or page3
        //if page3 back to page2

//        Socket socket = new Socket("localhost", 3306);
//        Client client= new Client(socket,username,passcode);
//
//        //infinite while loop
//        client.listenForMessage();
//        client.sendMessage();
        //enter the chat page
        //close the window and disconnect the socket


        stage=primaryStage;
        stage.setTitle("Central Perk Chatroom");
        stage.getIcons().add(new Image("light_bulb.png"));
        stage.setResizable(false);

        /*用户想要关闭窗口时chatroom首先弹出一个确认的小窗口*/
        showLogin();stage.setOnCloseRequest(event->{
            event.consume();
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Log Out");
            alert.setHeaderText(null);
            alert.setContentText(" Do you want to log out? ");

            Optional<ButtonType>result =alert.showAndWait();
            if (result.get()==ButtonType.OK){
                //相对于stage.close()方法，这个会连带着程序一起停止运行
                Platform.exit();
            }
        });

        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public void showLogin(){

    }
    public void showCreateUser(){

    }
    public void showChatbox(){

    }


}
