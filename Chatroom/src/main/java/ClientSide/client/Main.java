package ClientSide.client;


import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;


//整个client side 运行的主函数
// Tell the order of runnable program
//Event Handler for the event management

public class Main extends Application {

    public static void main(String[] args) {launch(args);}

    private Stage stage;

    private Scene scene;

    private Parent root;

    public void switchToPage1(ActionEvent )
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

    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
