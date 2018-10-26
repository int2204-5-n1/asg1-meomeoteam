package MainDictionary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ControllerAdd {
    @FXML
    TextField WordAdd;
    @FXML
    TextArea MeanAdd;
    public void ActionAdd(ActionEvent event) throws SQLException {
        String wordAdd = WordAdd.getText();
        String meanAdd = MeanAdd.getText();

        Controller controller = Main.getLoader().getController();

        if (wordAdd.equals("") && meanAdd.equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Bạn chưa nhập từ và nghĩa để thêm");
            alert.show();
        }
        else {
            controller.AddWord(wordAdd, meanAdd);

            Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }


}
