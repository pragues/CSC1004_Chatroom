package ServerSide;

import ClientSide.message.Message;
import ClientSide.message.MessageType;

import java.io.*;
import java.net.Socket;
import java.util.Date;

//Runnable si a class such that all the instance methods are implemented through different threads
public class ClientHandler implements Runnable{
    //every instance of this class. Keep track of our clients so that when broadcast a message everyone can see it
    //static: 让其属于这个class而不是属于一个object of this class

    /*This socket will be passed from our socket class
    *Establish a connection between the client and the server*/
    private final Socket socket;

    /*Used to read data specifically messages that have been sent from the client*/
    private final ObjectInputStream objectInputStream;

    /*Used to send data, or messages, to our client.
    * And these will be messages that have been sent from other clients
    * that will be broadcast by this arraylist. */
    private final ObjectOutputStream objectOutputStream;

    private final String client_name;

    /*constructor: Take parameters and uniquely identify any incoming requests.

    * Whenever we receive any request of client,
    * the server extracts its port number, the DataInputStream object and DataOutputStream object
    * and creates a new thread object of this class and invokes start() method on it.
    * */
    public ClientHandler(Socket socket, ObjectInputStream oi, ObjectOutputStream oo, String username){

            objectInputStream=oi;
            objectOutputStream=oo;
            client_name= username;
            this.socket=socket;

            System.out.println("ClientHandler: "+ client_name + " has entered the chatroom! ");

    }

    @Override
    public void run(){

        if (socket.isConnected()){System.out.println("dUI对对对: ");}
        while (socket.isConnected()){
            try {
                //This is a blocking operation: 只读取一行数据域
                Message newMessage=(Message) objectInputStream.readObject();
                broadcastMessage(newMessage);

            }catch (IOException | ClassNotFoundException e){
                closeEverything(socket,objectInputStream,objectOutputStream);
                break;  //break out when client disconnects
            }
        }
    }

    public void broadcastMessage(Message messageToSent){

        System.out.println("("+messageToSent.getSendTime()+")"+messageToSent.getName()+": "+messageToSent.getType());
        for (ClientHandler clientHandler: Server.cHandlers){
            try{
                clientHandler.objectOutputStream.writeObject(messageToSent);
                clientHandler.objectOutputStream.flush();

            }catch (IOException e){
                closeEverything(socket,objectInputStream,objectOutputStream);
            }
        }
    }

    /*When a client leaves the Chatroom. */
    public void removeClientHandler(){
        Server.cHandlers.remove(this);
        Message leaveMessage= new Message();
        leaveMessage.setType(MessageType.TEXT);
        leaveMessage.setSendTime(new Date());
        leaveMessage.setMsg("SYSTEM: "+client_name+ " has left the chatroom.");
        broadcastMessage(leaveMessage);
    }

    public void closeEverything(Socket socket, ObjectInputStream objectInputStream1, ObjectOutputStream objectOutputStream1) {

        removeClientHandler();
        try{
            /*For streams, we only need to close the wrappers. */
            if (objectInputStream1!=null){
                objectInputStream1.close();
            }
            if (objectOutputStream1!=null){
                objectOutputStream1.close();
            }
            if (socket!=null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
