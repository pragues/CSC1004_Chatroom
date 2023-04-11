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

        groupChatMessage.setEditable(false);
        groupChatMessage.setMouseTransparent(true);
    }

    /* 初始化用户信息和socket、client对象*/
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

                clientPrivate.sendMessage(username);
                clientPrivate.sendMessage(password);

                BufferedReader bufferedReader= clientPrivate.getBufferedReader();
                listenForMessage(socketPrivate, bufferedReader );

                //clientPrivate.listenForMessage();
                //String newMessageFromGroup= clientPrivate.listenForMessage();
                //System.out.println("newMessageFromGroupChat"+newMessageFromGroup);
                //addTextInScrollPane(newMessageFromGroup);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void listenForMessage(Socket socket, BufferedReader bufferedReader){
        //CREATE A NEW THREAD AND PASS the runnable object
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("<listen for messages>");

                Date date= new Date();
                long sendTime= date.getTime();

                while(socket.isConnected()){
                    try{
                        String receivedMessage;
                        receivedMessage= bufferedReader.readLine();

                        String out ="("+ date+sendTime+");"+receivedMessage;
                        //listen了之后没有返回任何东西我确实听到了但是没有什么用
                        //这里receive到了
                        System.out.println(receivedMessage+" ("+username+ ": listenForMessage)");

                        addTextInScrollPane(out);

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
    public LoggedInChatbox () {
        setUserInfo();

    }

    @FXML
    public void addTextInScrollPane(String msgWithUsername){
        groupChatMessage.appendText(" "+msgWithUsername+"\n"+"\n");
    }

    @FXML
    public void setSendMessage(ActionEvent event ){sendFunction();}

    @FXML
    public void setClearMessage(ActionEvent event){clearFunction();}

    public void clearFunction() {
        messageToSend.clear();
    }


    //TODO: 检查这里的parameter到底需不需要
    public void sendFunction() {

        if (socketPrivate.isConnected()){
            System.out.println("按了一次send(来自LoggedInChatbox ().send)");
        }

        if (socketPrivate.isConnected()&& !Objects.equals(message, "")){
            clientPrivate.sendMessage(message);  //可以使
            messageToSend.clear();
        }
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
