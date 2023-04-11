package ClientSide;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    private String password;
    private String receivedMessage;

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
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username=username;
            password=passcode;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        try{
            String mToSend= username+ ": "+message;

            //TODO： while 的问题
            if (socket.isConnected()&& !Objects.equals(message, "")){
                bufferedWriter.write(mToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

        }catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    public BufferedReader getBufferedReader(){
        return bufferedReader;
    }
    public String listenForMessage(){
        //CREATE A NEW THREAD AND PASS the runnable object
        String out;

        Date date= new Date();
        long sendTime= date.getTime();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("<listen for messages>");

                //String groupMessageFromServer;
                while(socket.isConnected()){
                    try{
                        receivedMessage= bufferedReader.readLine();

                        //listen了之后没有返回任何东西我确实听到了但是没有什么用
                        //这里receive到了
                        System.out.println(receivedMessage+" ("+username+ ": listenForMessage)");

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        out= "("+date+" "+sendTime+" ) "+ receivedMessage;
        System.out.println("OUT: "+ out);
        return out;
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try{
            if (bufferedReader!=null){
                bufferedReader.close();
            }
            if (bufferedWriter!=null){
                bufferedWriter.close();
            }
            if (socket !=null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
