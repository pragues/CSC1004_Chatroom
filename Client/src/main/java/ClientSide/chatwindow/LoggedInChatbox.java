package ClientSide.chatwindow;

import ClientSide.Client;
import ClientSide.login.Page2;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

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
    private String message;
    final static int ServerPort=1233;
    private void setUserInfo(){
        username= Page2.giveUsername();
        password= Page2.givePassword();
        System.out.println("Username: "+username+" Password: "+password +"(loggedInChatbox: setInfo)");
    }

    //为什么把这个变成class的函数就可以自动运行？
    public LoggedInChatbox () {
       try{
           setUserInfo();

           InetAddress ip= InetAddress.getByName("localhost");
           System.out.println("Client ip: "+ip+" (LoggedInChatbox) ");

           //创建本机的对象
           Socket socketNow= new Socket(ip, ServerPort);

           BufferedReader br=new BufferedReader(new InputStreamReader(socketNow.getInputStream()));
           BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(socketNow.getOutputStream()));


           Client clientNow=new Client(socketNow,username,password);
           clientNow.listenForMessage();

           while (socketNow.isConnected()){
               System.out.println("Connected(来自LoggedInChatbox ())");
               sendMessage(clientNow,br,bw);
           }
       }catch (IOException e){
           e.printStackTrace();
       }
    }

    public void sendMessage(Client client, BufferedReader br, BufferedWriter bw){
        //获取要发送的信息
        try {
            bw.write(username);
            bw.newLine();
            bw.flush();

            message= messageToSend.getText();

            if(!message.isBlank()){
                bw.write(username+": "+messageToSend);
                bw.newLine();
                bw.flush();
            }
        }catch (IOException E){
            E.printStackTrace();
        }
    }

    //chatbox send按钮的函数
    @FXML
    public void setSendMessage(ActionEvent event )throws IOException{sendFunction();}

    //chatbox clear 按钮的函数
    @FXML
    public void setClearMessage(ActionEvent event)throws IOException{clearFunction();}

    public void clearFunction() {
        String text =messageToSend.getText();

        messageToSend.setText("");
    }

    public void sendFunction() {
        String text = messageToSend.getText();
        System.out.println(username+": "+text);
        //TODO
        // 将已发送的消息显示


        messageToSend.setText("");
    }
    public void setOnKeyPressed(KeyEvent keyEvent) {
        //TODO
        // 按回车发消息
    }
}
