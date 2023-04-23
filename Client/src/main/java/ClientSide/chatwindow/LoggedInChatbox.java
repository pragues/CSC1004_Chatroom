package ClientSide.chatwindow;

import ClientSide.Client;
import ClientSide.login.Page2;
import ClientSide.message.Message;
import ClientSide.message.MessageType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.Date;

public class LoggedInChatbox  {

    @FXML
    private Button sendMessage;
    @FXML
    private Button clearMessage;
    @FXML
    private Button emojiList;
    @FXML
    private Button pictureSelection;
    @FXML
    private TextArea messageToSend;
    @FXML
    private TextFlow groupChatMessage;
    @FXML
    private ScrollPane scrollPaneForTextFlow;
    @FXML
    private TextFlow onlineUsers;
    @FXML
    private ScrollPane leftHand;

    private String username;
    private String password;
    private Socket socketPrivate;
    private Client clientPrivate;
    final static int ServerPort=1233;

    @FXML
    private void initialize(){

        scrollPaneForTextFlow.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPaneForTextFlow.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        leftHand.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        leftHand.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        groupChatMessage.setTextAlignment(TextAlignment.JUSTIFY);
        groupChatMessage.setPrefHeight(Region.USE_COMPUTED_SIZE);
        groupChatMessage.setPrefWidth(Region.USE_COMPUTED_SIZE);

    }

    public LoggedInChatbox () {setUserInfo();}

    /* 初始化用户信息和socket、client对象*/
    @FXML
    private void setUserInfo(){
        //string type
        username= Page2.giveUsername();
        password= Page2.givePassword();

        try{
            InetAddress ip= InetAddress.getByName("localhost");
            System.out.println("Client ip: "+ip+" (LoggedInChatbox) ");

            socketPrivate= new Socket(ip, ServerPort);
            clientPrivate=new Client(socketPrivate,username,password);

            Message initialUser = new Message();
            initialUser.setType(MessageType.USER);
            initialUser.setName(username);
            clientPrivate.sendMessage(initialUser);

            //todo:如果后期没有问题要不要改成while
            if (socketPrivate.isConnected()) {
                //收到来自群聊的消息
                ObjectInputStream objectInputStream= clientPrivate.getObjectInputStream();
                listenForMessage(socketPrivate, objectInputStream);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @FXML
    public void listenForMessage(Socket socket, ObjectInputStream objectInput){
        //CREATE A NEW THREAD AND PASS the runnable object
        new Thread(() -> {
            System.out.println("<listen for messages>");

            while(socket.isConnected()){
                try{
                    //todo: bug: socket.closed();
                    Message receivedMessage= (Message) objectInput.readObject();
                    MessageType messageType= receivedMessage.getType();

                    if (messageType==MessageType.PICTURE){
                        String senderUsernameWithDate="("+receivedMessage.getSendTime()+")" +"\n"+receivedMessage.getName()+": ";
                        //得到url
                        String newImageMessage= receivedMessage.getPicture();
                        addPictureToTextFlow(senderUsernameWithDate, newImageMessage);
                        System.out.println("newImageMessage: "+newImageMessage);

                    }

                    if (messageType==MessageType.EMOJI){
                        //TODO: 发emoji的时候加到聊天框里面

                    }
                    if (messageType==MessageType.TEXT){
                        String newGroupMessage= receivedMessage.getMsg();
                        String senderUsername= receivedMessage.getName();
                        Date sendDate= receivedMessage.getSendTime();
                        String toBeAppended= "("+sendDate+")"+"\n"+senderUsername+": "+newGroupMessage;
                        addTextToTextFlow(toBeAppended);
                    }

                    if (messageType==MessageType.USER){
                        String userInfo= receivedMessage.getName();
                        addNewUser(userInfo);
                    }

                }catch (IOException | ClassNotFoundException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @FXML
    public void addNewUser(String user){
        Platform.runLater(()->{
            Text newUser= new Text(user+"\n");
            newUser.setTextAlignment(TextAlignment.JUSTIFY);
            newUser.setFont(Font.font("Britannic Bold", FontWeight.BOLD, FontPosture.REGULAR, 17));
            onlineUsers.getChildren().add(newUser);
        });
    }
    @FXML
    public void addTextToTextFlow(String messageWithUsername){
        //Paragraphs are separated by '\n' present in any Text child
        // todo: not on javafx application thread, 不是Javafx的thread: 需要用 Platform.runLater ！！！

        Platform.runLater(()->{
                    Text textMessage= new Text("\n"+messageWithUsername+"\n");
                    textMessage.setTextAlignment(TextAlignment.JUSTIFY);
                    textMessage.setFont(Font.font("Britannic Bold", FontWeight.BOLD, FontPosture.REGULAR, 17));
                    groupChatMessage.getChildren().add(textMessage);
        });
    }

    @FXML
    public void addPictureToTextFlow(String sender, String picUrl){
        //todo: 对于已经收到的图片消息:现在能收到消息但是无法在textFlow上显示
        Platform.runLater (()->{
            //可以了！！！！
            Image image = new Image(picUrl);
            ImageView imageView= new ImageView(image);
            Text userInfo= new Text("\n"+sender+": \n");
            userInfo.setTextAlignment(TextAlignment.JUSTIFY);
            userInfo.setFont(Font.font("Britannic Bold", FontWeight.BOLD, FontPosture.REGULAR, 17));
            groupChatMessage.getChildren().addAll(userInfo, imageView);
        });

    }
    @FXML
    public void setSendMessage(ActionEvent event ){
        //todo: Send Button 的正经函数: 现在只能发送文字消息

        Message message1 = new Message();
        message1.setSendTime(new Date());
        message1.setType(MessageType.TEXT);
        message1.setMsg(messageToSend.getText());
        message1.setName(username);

        if (socketPrivate.isConnected()){System.out.println("按了一次send(来自LoggedInChatbox ().send)");}

        if (socketPrivate.isConnected()&& !(messageToSend.getText()).equals("")){
            clientPrivate.sendMessage(message1);  //可以使
            messageToSend.clear();
        }
    }

    @FXML
    public void setClearMessage(ActionEvent event){messageToSend.clear();}

    @FXML
    public void setEmojiList(ActionEvent event){
        try{
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmojiList.fxml"));
//            Parent root = loader.load();

            URL fxmlLocation = getClass().getResource("/EmojiList.fxml");
            Parent root= FXMLLoader.load(fxmlLocation);
            
            Scene scene = new Scene(root, 392, 300);
            Stage primaryStage= (Stage)((Node)event.getSource()).getScene().getWindow();
            primaryStage.setTitle("Emojis List");
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }


    //TODO: 选择文件夹中的图片，在会话框中显示，能够通过socket connection 发送出去
    @FXML
    public void setPictureSelection(ActionEvent event) {

        String picAbsPath;
        String picRltPath;
        Stage fileDisplayer= new Stage();

        FileChooser fileChooser= new FileChooser();
        fileChooser.setTitle( "Choose your file to send");

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));

        Label label = new Label("No file is being selected");

        File file= fileChooser.showOpenDialog(fileDisplayer);

        if (file!=null){
            picAbsPath= file.getAbsolutePath();
            picRltPath= file.getPath();

            URI uri1= file.toURI();
            try {
                URL url1=uri1.toURL();
                FileInputStream fileInputStream= new FileInputStream(picAbsPath);
                Image image= new Image(fileInputStream);

                Message picMessage= new Message();
                picMessage.setPicture(url1.toString());
                picMessage.setName(username);
                picMessage.setType(MessageType.PICTURE);
                picMessage.setSendTime(new Date());
                clientPrivate.sendMessage(picMessage);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e){
                e.printStackTrace();
            }

            label.setText(picAbsPath + "is being selected");
            System.out.println("The realative path of the pic: "+picRltPath);

                //todo: display image 然后选择发送的部分
//                Image image= new Image(new FileInputStream(picAbsPath));
//                ImageView imageView= new ImageView(image);
//                imageView.setImage(image);
//                imageView.setFitHeight(460);
//                imageView.setFitWidth(510);
//                Group root= new Group(imageView);
//                Scene scene= new Scene(root, 600, 500 );
//                fileDisplayer.setTitle("Image Preview");
//                fileDisplayer.setResizable(false);
//                fileDisplayer.setScene(scene);
//                fileDisplayer.show();

                //发送picMessage的时候
                //fileDisplayer.close();

        }

    }

    @FXML
    public void setOnKeyPressed(KeyEvent keyEvent) {
        //todo: 同时按space和shift 的发消息的快捷键
        if (keyEvent.getCode()== KeyCode.ENTER){
            Message message1= new Message();
            message1.setType(MessageType.TEXT);
            message1.setSendTime(new Date());
            message1.setName(username);
            message1.setMsg(messageToSend.getText());
            clientPrivate.sendMessage(message1);
            messageToSend.clear();
        }

    }


}
