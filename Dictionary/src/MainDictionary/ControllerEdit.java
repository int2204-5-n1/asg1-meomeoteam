package MainDictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ControllerEdit {
    @FXML
    private TextField WordEdit;
    @FXML
    private TextArea MeanEdit;

    //Tạo sự kiên cho button OK
    public void ActionEdit(ActionEvent event){
        String wordEdit = WordEdit.getText();
        System.out.printf(wordEdit);
        String meanEdit = MeanEdit.getText();
        System.out.printf(meanEdit);
    }
}
