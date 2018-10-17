package MainDictionary;

import ReadDictionary.ReadDic;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class ControllerAdd{
    @FXML
    private TextField WordAdd;
    @FXML
    private TextArea MeanAdd;
    ReadDic readDic = new ReadDic();
    private final String FileEV = "E:\\asg1-meomeoteam\\data\\E_V.txt";

    //Tạo sự kiên cho Button OK
    public void ActionAdd (ActionEvent event){
        String wordAdd = WordAdd.getText();
        String meanAdd = MeanAdd.getText();
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(FileEV, true);
            bw = new BufferedWriter(fw);
            String word = wordAdd + "<html>" + meanAdd + "</html>";
            bw.newLine();
            bw.write(word);

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
        System.out.printf(meanAdd);
    }
}
