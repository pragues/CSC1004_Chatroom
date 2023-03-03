package ServerSide;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import ClientSide.client.chatwindow.ClientHandler;
//import ClientSide.client.chatwindow.ClientHandler;

public class Server extends Application {
    /*As long as there is a client connecting in, we will
     * generate a thread. */
    private ServerSocket serverSocket;  //Maybe need to add it final

    public Server(ServerSocket serverSocket){
        this.serverSocket=serverSocket;
    }

    public void startServer(){
        /*Make the server generally running indefinitely*/
        try {
            while(!serverSocket.isClosed()){
                //The program will halt here until a client connects;
                Socket socket= serverSocket.accept();
                System.out.println("A new client has connected! ");


                ClientHandler clientHandler= new ClientHandler(socket);  //TODO

                //*@ 这里是multithreading 的内容！
                // 虽然并不是看的很懂
                // ----------------------------------
                // TAT 呜呜呜呜
                // */
                Thread thread = new Thread(clientHandler);

                thread.start();

            }
        }catch (IOException e){
            //TODO
        }
    }

    public void closeServerSocket(){
        try {
            //nul pointer exception
            if (serverSocket!= null){
                serverSocket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException{
        //launch(args);
        ServerSocket serverSocket1 = new ServerSocket(1233);
        //The server object of this class
        Server server =new Server(serverSocket1);
        server.startServer();
    }

    @Override
    public void start(Stage primaryStage) {}
}
