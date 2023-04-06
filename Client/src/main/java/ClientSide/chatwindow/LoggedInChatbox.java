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

    //发送之后重载成“ ”
    private String message="";    //初始化解决 “The message could be null ”的问题

    final static int ServerPort=1233;
    private void setUserInfo(){
        username= Page2.giveUsername();
        password= Page2.givePassword();
        System.out.println("Username: "+username+" Password: "+password +"(loggedInChatbox: setInfo)");
    }

    public LoggedInChatbox () {
        System.out.println("first step: ");
        setUserInfo();

       try{
           InetAddress ip= InetAddress.getByName("localhost");
           System.out.println("Client ip: "+ip+" (LoggedInChatbox) ");
           //创建本机的对象
           Socket socketNow= new Socket(ip, ServerPort);

           //BufferedReader br=new BufferedReader(new InputStreamReader(socketNow.getInputStream()));
           //BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(socketNow.getOutputStream()));

           Client clientNow=new Client(socketNow,username,password);

           //TODO:这个没有起作用
           clientNow.listenForMessage();

           if (socketNow.isConnected()){
               System.out.println("Connected(来自LoggedInChatbox ())");
           }
           while (socketNow.isConnected()){

               clientNow.sendMessage(message);  //可以使

               addTextInScrollPane(username, message);

           }
       }catch (IOException e){
           e.printStackTrace();
       }
    }

    public void addTextInScrollPane(String userName, String msg){
        //TODO：这个怎么保证每次发送完消息之后message已经clear了？
    }


    //chatbox send按钮的函数
    @FXML
    public void setSendMessage(ActionEvent event ){sendFunction();}

    @FXML
    public void setClearMessage(ActionEvent event){clearFunction();}

    public void clearFunction() {
        messageToSend.clear();
    }

    public void sendFunction() {
        System.out.println(username+": "+message+"(ActionEvent send printing)");
        addTextInScrollPane(username, message);
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
