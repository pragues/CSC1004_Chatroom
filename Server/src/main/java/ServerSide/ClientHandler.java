package ServerSide;

import ClientSide.login.Page2;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

//Runnable si a class such that all the instance methods are implemented through different threads
public class ClientHandler implements Runnable{
    //every instance of this class. Keep track of our clients so that when broadcast a message everyone can see it
    //static: 让其属于这个class而不是属于一个object of this class
    public ArrayList<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();

    /*This socket will be passed from our socket class
    *Establish a connection between the client and the server*/
    private Socket socket;

    /*Used to read data specifically messages that have been sent from the client*/
    private BufferedReader bufferedReader;

    /*Used to send data, or messages, to our client.
    * And these will be messages that have been sent from other clients
    * that will be broadcast by this arraylist. */
    private BufferedWriter bufferedWriter;

    private String client_name;
    private String pass_word;
    private String message;

    /*constructor: Take parameters and uniquely identify any incoming requests.
    * para: socket, datainputstream, dataoutputstream
    * Whenever we receive any request of client,
    * the server extracts its port number, the DataInputStream object and DataOutputStream object
    * and creates a new thread object of this class and invokes start() method on it.
    * */
    public ClientHandler(Socket socket, BufferedReader br, BufferedWriter bw, String username, String msg){

            bufferedReader=br;
            bufferedWriter=bw;
            client_name= username;
            message= msg;
            this.socket=socket;
            clientHandlers.add(this);
            //broadcastMessage(msg);
            System.out.println("ClientHandler: "+ client_name + " has entered the chatroom! ");

    }

    @Override
    public void run(){
        String newMessage;

        while (socket.isConnected()){

            try {
                //This is a blocking operation: 只读取一行数据域
                newMessage=bufferedReader.readLine();
                System.out.println("dUI对对对: "+newMessage);

                //TODO: 这之后不能使
                broadcastMessage(newMessage);

            }catch (IOException e){
                closeEverything(socket,bufferedReader,bufferedWriter);
                break;  //break out when client disconnects

            }
        }
    }

    public void broadcastMessage(String messageToSent){
        //For each client-handler, for each iteration
        for (ClientHandler clientHandler: clientHandlers){
            try{
                if (!clientHandler.client_name.equals(client_name)) {
                    clientHandler.bufferedWriter.write(messageToSent);

                    System.out.println(" 来自server的：clientHandler.bufferedWriter.write(messageToSent);");
                    clientHandler.bufferedWriter.newLine();  //equivalent to press an enter key
                    clientHandler.bufferedWriter.flush();    //a buffer might not be full, not we manually flush it
                }
            }catch (IOException e){
                closeEverything(socket,bufferedReader,bufferedWriter);
            }
        }
    }

    /*When a client leaves the Chatroom. */
    public void removeClientHandler(){
        //setInfo();
        clientHandlers.remove(this);
        broadcastMessage("SERVER: "+client_name+ " has left the chatroom.");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {

        removeClientHandler();
        try{
            /*For streams, we only need to close the wrappers. */
            if (bufferedReader!=null){
                bufferedReader.close();
            }
            if (bufferedWriter!=null){
                bufferedWriter.close();
            }
            if (socket!=null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
