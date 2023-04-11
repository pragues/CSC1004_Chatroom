package ClientSide.chatwindow;

import ClientSide.Client;
import ClientSide.login.Page2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.Objects;

public class LoggedInChatbox {
    private String username;
    private String password;
    @FXML
    private Button sendMessage;
    @FXML
    private Button clearMessage;
    @FXML
    private Button emojiList;
    @FXML
    private TextArea messageToSend;

    private Socket socketPrivate;
    private Client clientPrivate;

    @FXML
    private TextArea groupChatMessage;

    private String message="";    //解决初始化“The message could be null ”的问题

    final static int ServerPort=1233;

    @FXML
    private void initialize(){
        //fxml 对应application的ini()
        groupChatMessage.setEditable(false);
    }

    /* @ 初始化用户信息和socket、client对象*/
    @FXML
    private void setUserInfo(){
        username= Page2.giveUsername();
        password= Page2.givePassword();

        try{
            InetAddress ip= InetAddress.getByName("localhost");
            System.out.println("Client ip: "+ip+" (LoggedInChatbox) ");

            socketPrivate= new Socket(ip, ServerPort);
            clientPrivate=new Client(socketPrivate,username,password);

            //todo:如果后期没有问题要不要改成while
            if (socketPrivate.isConnected()) {
                clientPrivate.listenForMessage();
            }
            clientPrivate.sendMessage(username);
            clientPrivate.sendMessage(password);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public LoggedInChatbox () {
        setUserInfo();
    }

    public void addTextInScrollPane(String userName, String msg){

        Date date= new Date();
        String sendTime= String.valueOf(date.getTime());

        groupChatMessage.appendText(userName+" ("+date+" "+sendTime+")"+": "+msg+"\n"+"\n");
    }

    @FXML
    public void setSendMessage(ActionEvent event ){sendFunction(clientPrivate, socketPrivate);}

    @FXML
    public void setClearMessage(ActionEvent event){clearFunction();}

    public void clearFunction() {
        messageToSend.clear();
    }


    //TODO: 检查这里的parameter到底需不需要
    public void sendFunction(Client client, Socket socket ) {

        if (socketPrivate.isConnected()){
            System.out.println("Connected(来自LoggedInChatbox ().send)");
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
