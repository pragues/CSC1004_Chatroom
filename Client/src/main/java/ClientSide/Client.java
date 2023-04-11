package ClientSide;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
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
//            bufferedWriter.write(username);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
//            bufferedWriter.write(password);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();

            //TODO
            String mToSend= username+ ": "+message;

            //TODO： while 的问题
            if (socket.isConnected()&& !Objects.equals(message, "")){
                bufferedWriter.write(mToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                System.out.println(message);
            }

        }catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    public void listenForMessage(){
        //CREATE A NEW THREAD AND PASS the runnable object
        //listen for group message from the server
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("<listen for messages>");
                String messageSender;
                String messagePass;
                String groupMessageFromServer;
                while(socket.isConnected()){
                    try{
                        messageSender= bufferedReader.readLine();
                        messagePass= bufferedReader.readLine();
                        groupMessageFromServer= bufferedReader.readLine();  //这里的reader是client这一边的reader？

                        System.out.println(groupMessageFromServer+" ("+username+ " :listenForMessage)");
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();

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
