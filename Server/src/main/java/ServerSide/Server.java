package ServerSide;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


/*-----------------------------------------------------------
*The class that create a server and establish a server socket
*Port number : 1233
*The main class of the server module
* ------------------------------------------------------------*/
public class Server {

    private final ServerSocket serverSocket;  //可能要final

    //这个是因为client-handler里面所以改成static的
    static ArrayList<ClientHandler> cHandlers = new ArrayList<>();
    public Server(ServerSocket serverSocket){
        this.serverSocket=serverSocket;
    }

    public void startServer(){
        /*Make the server generally running indefinitely*/
        try {
            while(!serverSocket.isClosed()){
                //The program will halt here until a client connects;
                Socket socket= serverSocket.accept();
                System.out.println("\n"+"A new client has connected! " + socket);

                //obtain the input and output stream on the server side
                ObjectInputStream objectInputStream= new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream objectOutputStream= new ObjectOutputStream(socket.getOutputStream());

                //从这里首先读取username和password
                String un= String.valueOf(objectInputStream.read());
                String pw= String.valueOf(objectInputStream.read());
                System.out.println("username:"+un+"+ password: "+pw+ "(startServer)");

                ClientHandler clientHandler= new ClientHandler(socket, objectInputStream, objectOutputStream, un );

                Thread thread = new Thread(clientHandler);

                cHandlers.add(clientHandler);

                thread.start();

            }

        }catch (IOException e){
            e.printStackTrace();
            closeServerSocket();
        }
    }

    public void closeServerSocket(){
        try {
            if (serverSocket!= null){
                serverSocket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket1 = new ServerSocket(1233);
        Server server =new Server(serverSocket1);
        server.startServer();
    }

}
