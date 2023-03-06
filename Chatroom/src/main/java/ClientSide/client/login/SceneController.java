package ClientSide.client.login;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.awt.event.ActionEvent;

public class SceneController {
    public static void switchScene(ActionEvent event, String fxmlFile, String title, String username, String password){

        Parent root= null;

        if (username!=null && password!=null){
            //这一行是是要转到要转入的scene去

            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource(fxmlFile));
        }
    }
}
