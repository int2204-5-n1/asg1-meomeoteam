package MainDictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ControllerEdit {
    @FXML
    protected TextField ChooseWord;
    @FXML
    protected TextField WordEdit;
    @FXML
    protected TextArea MeanEdit;

    public void Edit(ActionEvent event) throws SQLException {
        String chooseWord = ChooseWord.getText();
        String wordEdit = WordEdit.getText();
        String meanEdit = MeanEdit.getText();
        Controller controller = Main.getLoader().getController();
        controller.EditWord(chooseWord,wordEdit,meanEdit);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
