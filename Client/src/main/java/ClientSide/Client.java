package ClientSide;

import ClientSide.message.Message;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class Client {
    private Socket socket;

    private ObjectOutputStream oos;
    private InputStream inputStream;
    private ObjectInputStream ins;
    private OutputStream outputStream;

    private String username;
    private String password;

    /*Function: Constructor of Client
    * Usage: Client client1= new Client(socket, username, passcode);
    * --------------------------------------------------------------
    *When Logging in, we first set the username and passcode, then create
    * the socket and connect to the database, then set the Client object
    * for this specific client.
    * */
    public Client(Socket socket, String username, String passcode){
        try{
            this.socket=socket;
            //this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputStream=socket.getOutputStream();
            oos= new ObjectOutputStream(outputStream);
            inputStream=socket.getInputStream();
            ins= new ObjectInputStream(inputStream);
            this.username=username;
            password=passcode;

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message){
        try{
            //将发消息的人变成一个message的属性
            //TODO： while 的问题
            if (socket.isConnected()&& !Objects.equals(message, "")){
                System.out.println(message.getName()+": "+message.getType());
                oos.writeObject((Message) message);
                oos.flush();
            }

        }catch (IOException e){
            closeEverything(socket,ins,oos);
        }
    }

    public ObjectInputStream getObjectInputStream(){
        return ins;
    }

    public void closeEverything(Socket socket,ObjectInputStream oin, ObjectOutputStream oout){
        try{
            if (oin!=null){
                oin.close();
            }
            if (oout!=null){
                oout.close();
            }
            if (socket !=null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
