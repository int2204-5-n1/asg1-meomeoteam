package MainDictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class ControllerAdd {
    @FXML
    private TextField WordAdd;
    @FXML
    private TextArea MeanAdd;
    //Tạo sự kiên cho Button OK
    public void ActionAdd (ActionEvent event){
        String wordAdd = WordAdd.getText();
        System.out.println(wordAdd);
        String meanAdd = MeanAdd.getText();
        System.out.printf(meanAdd);
    }

}
