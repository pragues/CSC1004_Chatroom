package ClientSide.chatwindow;

import ClientSide.Client;
import ClientSide.login.Page2;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;

public class LoggedInChatbox {
    private String username;
    private String password;
    @FXML
    private Button sendMessage;
    @FXML
    private Button clearMessage;
    @FXML
    private TextArea messageToSend;
    @FXML
    private ScrollPane scrollPane;
    private Socket socketPrivate;
    private Client clientPrivate;

    private String message="";    //解决初始化“The message could be null ”的问题

    final static int ServerPort=1233;

    /* @ 初始化用户信息和socket、client对象*/
    private void setUserInfo(){
        username= Page2.giveUsername();
        password= Page2.givePassword();

        try{
            InetAddress ip= InetAddress.getByName("localhost");
            System.out.println("Client ip: "+ip+" (LoggedInChatbox) ");

            socketPrivate= new Socket(ip, ServerPort);
            clientPrivate=new Client(socketPrivate,username,password);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public LoggedInChatbox () {
        setUserInfo();
//
//       try{
//           InetAddress ip= InetAddress.getByName("localhost");
//           System.out.println("Client ip: "+ip+" (LoggedInChatbox) ");
//
//           Socket socketNow= new Socket(ip, ServerPort);
//
//           //BufferedReader br=new BufferedReader(new InputStreamReader(socketNow.getInputStream()));
//           //BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(socketNow.getOutputStream()));
//
//           Client clientNow=new Client(socketNow,username,password);
//
//           //TODO:这个没有起作用
//           //clientNow.listenForMessage();
//
//           if (socketNow.isConnected()){
//               System.out.println("Connected(来自LoggedInChatbox ())");
//               clientNow.sendMessage("diyige:"+message);
//           }
//
//           //String messageNow= messageToSend.getText();
//           //TODO: while的问题
//           if (socketNow.isConnected()&& !Objects.equals(message, "")){
//               clientNow.sendMessage(message);  //可以使
//               addTextInScrollPane(username, message);
//
//           }
//
//
//       }catch (IOException e){
//           e.printStackTrace();
//       }
    }

    public void addTextInScrollPane(String userName, String msg){
        //TODO：这个怎么保证每次发送完消息之后message已经clear了？
    }


    @FXML
    public void setSendMessage(ActionEvent event ){sendFunction(clientPrivate, socketPrivate);}

    @FXML
    public void setClearMessage(ActionEvent event){clearFunction();}

    public void clearFunction() {
        messageToSend.clear();
    }


    public void sendFunction(Client client, Socket socket ) {
        System.out.println(username+": "+message+"(ActionEvent send printing)");

        if (socketPrivate.isConnected()){
            System.out.println("Connected(来自LoggedInChatbox ())");
            //clientPrivate.sendMessage("diyige:"+message);
        }

        if (socketPrivate.isConnected()&& !Objects.equals(message, "")){
            clientPrivate.sendMessage(message);  //可以使
            addTextInScrollPane(username, message);
        }
        messageToSend.clear();
    }


    @FXML
    public void setOnKeyPressed(KeyEvent keyEvent) {
        //TODO
        // 按回车发消息
    }

    @FXML
    public void setMessage(KeyEvent keyEvent){
        message=messageToSend.getText();
    }

}
