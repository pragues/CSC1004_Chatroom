package ClientSide.client.login;

import java.net.Socket;
import java.sql.*;

public class ConnectToDatabase {

    private Socket socket;
    private String username;
    private String passcode;

    public void makeConnections(Socket socket, String username, String passcode){
        this.socket=socket;
        this.username=username;
        this.passcode=passcode;

    }
    public void putInfoToDatabase(){
        PreparedStatement ps= null;
        ResultSet rs= null;
        Connection ct= null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/Chatroom", "root","");


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
