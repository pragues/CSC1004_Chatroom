package ServerSide;

import ClientSide.login.Page2;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

//Runnable si a class such that all the instance methods are implemented through different threads
public class ClientHandler implements Runnable{
    //every instance of this class. Keep track of our clients so that when broadcast a message everyone can see it
    //static: 让其属于这个class而不是属于一个object of this class
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    /*//this socket will be passed from our socket class
    *Establish a connection between the client and the server*/
    private Socket socket;

    /*Used to read data specifically messages that have been sent from the client*/
    private BufferedReader bufferedReader;

    /*Used to send data, or messages, to our client.
    * And these will be messages that have been sent from other clients
    * that will be broadcast by this arraylist. */
    private BufferedWriter bufferedWriter;

    private String client_name;
    private String password;

    private void setInfo(){
        client_name= Page2.giveUsername();
        password= Page2.givePassword();
    }
    /*constructor: Take parameters and uniquely identify any incoming requests.
    * para: socket, datainputstream, dataoutputstream
    * Whenever we receive any request of client,
    * the server extracts its port number, the DataInputStream object and DataOutputStream object
    * and creates a new thread object of this class and invokes start() method on it.*/
    public ClientHandler(Socket socket, BufferedReader br, BufferedWriter bw){
        //
        this.socket=socket;
        bufferedReader=br;
        bufferedWriter=bw;
        //wrap the byte-stream in character stream
        //this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));

        setInfo();

        clientHandlers.add(this);

        broadcastMessage("SERVER: "+ client_name + " has entered the chatroom! ");
        System.out.println("SERVER: "+ client_name + " has entered the chatroom! ");
    }


    /*这里面的是每一个Thread都会run的东西 */
    @Override
    public void run(){

        String message_from_client;

        while (socket.isConnected()){
            try {
                //还是需要在TextArea和bufferedReader 之间建立联系
                // TODO
                message_from_client=bufferedReader.readLine(); //This is a blocking operation
                broadcastMessage(message_from_client);
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
                //why clientHandler.client_name could be null? 莫不是因为我只开了一个client？
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
