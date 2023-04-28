package ServerSide;

import ClientSide.message.Message;
import ClientSide.message.MessageType;

import java.io.*;
import java.net.Socket;
import java.util.Date;

//Runnable is a class such that all the instance methods are implemented through different threads
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

    private String client_name;

    /*constructor: Take parameters and uniquely identify any incoming requests.

    * Whenever we receive any request of client,
    * the server extracts its port number, the DataInputStream object and DataOutputStream object
    * and creates a new thread object of this class and invokes start() method on it.
    * */
    public ClientHandler(Socket socket, ObjectInputStream oi, ObjectOutputStream oo){

        objectInputStream=oi;
        objectOutputStream=oo;
        this.socket=socket;

        System.out.println("ClientHandler: a new client has entered the chatroom! ");
    }

    //public String thisUsername(){return client_name;}
    @Override
    public void run(){

        if (socket.isConnected()){System.out.println("dUI对对对: ");}
        while (socket.isConnected()){
            try {
                //This is a blocking operation: 只读取一行数据域
                Message newMessage=(Message) objectInputStream.readObject();
                MessageType newType =newMessage.getType();

                if (newType==MessageType.USER){
                    String clientName=newMessage.getName();
                    Server.allUsername.add(clientName);
                    System.out.println(Server.allUsername);   //todo: 这里的ArrayList更新的额是正确的
                    newMessage.setOnlineUsers(Server.allUsername);
                }
                //是因为你broadcast仍然是你播报的时间点的ArrayList吗
                broadcastMessage(newMessage);
            }catch (IOException | ClassNotFoundException e){
                closeEverything(socket,objectInputStream,objectOutputStream);
                break;  //break out when client disconnects
            }
        }
    }

    public void broadcastMessage(Message messageToSent){
        //给每个人发的要是一样的东西
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
