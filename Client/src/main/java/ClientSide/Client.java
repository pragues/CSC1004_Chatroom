package ClientSide;

import java.io.*;
import java.net.Socket;
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

//    public static void main(String[] args) throws IOException {
//        Scanner scanner =new Scanner(System.in);
//        System.out.println("Enter your user name for the group-chat: ");
//        String username= scanner.nextLine();
//        String passcode= scanner.nextLine();
//        Socket socket = new Socket("localhost", 3306);
//        Client client= new Client(socket,username,passcode);
//
//        //infinite while loop
//        client.listenForMessage();
//        client.sendMessage();
//    }


}
