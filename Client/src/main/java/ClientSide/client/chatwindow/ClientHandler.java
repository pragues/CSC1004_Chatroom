package ClientSide.client.chatwindow;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import java.sql.*;

public class ClientHandler implements Runnable{
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    //this socket will be passed from our socket class
    /*Establish a connection between the client and the server*/
    private Socket socket;

    /*Used to read data specifically messages that have been sent from the client*/
    private BufferedReader bufferedReader;

    /*Used to send data, or messages, to our client.
    * And these will be messages that have been sent from other clients
    * that will be broadcast by this arraylist. */
    private BufferedWriter bufferedWriter;

    private String client_name;
    private String password;

    /*constructor: Take parameters and uniquely identify any incoming requests.
    * para: socket, datainputstream, dataoutputstream
    * Whenever we receive any request of client,
    * the server extracts its port number, the DataInputStream object and DataOutputStream object
    * and creates a new thread object of this class and invokes start() method on it.*/
    public ClientHandler(Socket socket){
        try {
            //
            this.socket=socket;
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //wrap the byte-stream in character stream
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //是不是在这里连到数据库里面去？？
            this.client_name= bufferedReader.readLine();
            this.password=bufferedReader.readLine();

            clientHandlers.add(this);
            broadcastMessage("SERVER: "+ client_name + " has entered the chatroom! "); //TODO
        }catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);  //TODO
        }
    }

    /*Listening for messages. */
    @Override
    public void run(){
        String message_from_client;
        while (socket.isConnected()){
            try {
                message_from_client=bufferedReader.readLine(); //This is a blocking application
                broadcastMessage(message_from_client);
            }catch (IOException e){
                closeEverything(socket,bufferedReader,bufferedWriter);
                break;  //break out when disconnect
            }
        }
    }

    public void broadcastMessage(String messageToSent){
        //For each client-handler, for each iteration
        for (ClientHandler clientHandler: clientHandlers){
            try{
                if (!clientHandler.client_name.equals(client_name)){
                    clientHandler.bufferedWriter.write(messageToSent);
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
