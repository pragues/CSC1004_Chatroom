package ClientSide.client.chatwindow;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import ClientSide.client.chatwindow.ClientHandler;
import com.mysql.cj.xdevapi.Client;
import com.mysql.cj.xdevapi.Session;

public class  client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public client(Socket socket, String username, String passcode){
        try{
            this.socket=socket;
            this.bufferedWriter=new BufferedReader(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader=new BufferedWriter(new InputStreamReader(socket.getInputStream()));
            this.username=username;
        }catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage(){
        try{
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner =  new Scanner(System.in);
            while(socket.isConnected()){
                String messageToSend =scanner.nextLine();
                bufferedWriter.write(username+": "+messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }catch (IOException e){

            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    public void listenForMessage(){
        //CREATE A NEW THREAD AND PASS the runnable object
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;
                while (socket.isConnected()){
                    try{
                        msgFromGroupChat= bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);
                    }catch (IOException e){
                        closeEverything(socket,bufferedReader,bufferedWriter);
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

    public static void main(String[] args) throws IOException {
        Scanner scanner =new Scanner(System.in);
        System.out.println("Enter your user name for the group-chat: ");
        String username= scanner.nextLine();
        String passcode= scanner.nextLine();
        Socket socket = new Socket("localhost", 3306);
        client client= new client(socket,username,passcode);

        //infinite while loop
        client.listenForMessage();
        client.sendMessage();
    }


}
