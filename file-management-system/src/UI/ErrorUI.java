package UI;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class ErrorUI {

    public static void alertDialog(String p_header, String p_message,Stage rootStage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(p_header);
        alert.setContentText(p_message);
        alert.initOwner(rootStage);
        alert.show();
    }

}
