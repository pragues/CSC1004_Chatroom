package ServerSide;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


/*The class that create a server and establish a server socket
*Port number : 1233
* The main class of the server module
* */
public class Server {
    /*As long as there is a client connecting in, we will
     * generate a thread. */
    private ServerSocket serverSocket;  //可能要final

    public Server(ServerSocket serverSocket){
        this.serverSocket=serverSocket;
    }

    public void startServer(){
        /*Make the server generally running indefinitely*/
        try {
            while(!serverSocket.isClosed()){
                //The program will halt here until a client connects;
                Socket socket= serverSocket.accept();
                System.out.println("A new client has connected! " + socket);

                //obtain the input and output stream on the server side
                BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                ClientHandler clientHandler= new ClientHandler(socket, bufferedReader,bufferedWriter);  //TODO

                //multi threading
                Thread thread = new Thread(clientHandler);

                thread.start();
            }
        }catch (IOException e){
            e.printStackTrace();
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

}
