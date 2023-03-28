package ClientSide.login;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class JDBC {
    public JDBC() {
    }

    Connection con;

    public void initConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user_info", "root", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void closeConnection() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void showAllData() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from users");
            while (rs.next()) {
                System.out.println("rol1:" + rs.getString("username"));
                System.out.println("rol2:" + rs.getString("password"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //
    public boolean searchForId(String id) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from users");
            while (rs.next()) {
                if (rs.getString("username").equals(id)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean confirmForPassword(String id, String ps) {
        try{
            //TODO
            Statement stmt = con.createStatement();
            //Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT username,password FROM users");
            while (rs.next()) {
                if (rs.getString("username").equals(id)&&rs.getString("password").equals(ps)) {
                    return true;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void add(String id,String pw){
        try{
            String sql ="insert into users values(?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,id);
            ps.setString(2,pw);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}