package MainDictionary;

import ReadDictionary.ReadDic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class ControllerAdd{
    @FXML
    private TextField WordAdd;
    @FXML
    private TextArea MeanAdd;
    private final String FileEV = "E:\\asg1-meomeoteam\\Dictionary\\src\\data\\E_V.txt";
    ReadDic readDic = new ReadDic();
    public void AddWord(ActionEvent event){
        String wordAdd = WordAdd.getText();
        String meanAdd = MeanAdd.getText();
//        Controller controller = new Controller();
//        controller.add(wordAdd,meanAdd);
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(FileEV, true);
            bw = new BufferedWriter(fw);
            String word = wordAdd + "<html>" + "<ul>" + meanAdd + "</ul>" + "</html>";
            bw.newLine();
            bw.write(word);
            readDic.keys.add(wordAdd);
            readDic.data.put(meanAdd,wordAdd);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(bw != null)
                    bw.close();
                if(fw != null)
                    fw.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        System.out.println(wordAdd);
        System.out.println(meanAdd);
    }
}