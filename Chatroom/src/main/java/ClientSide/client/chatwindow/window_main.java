package ClientSide.client.chatwindow;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.stage.Stage;


//整个client side 运行的主函数
// Tell the order of runnable program

public class window_main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

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

        //enter the chat page
        //close the window and disconnect the socket

    }


    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
