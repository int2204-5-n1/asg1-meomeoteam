package sample;

import Dic.ReadDic;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    //tao su kien Quit Menu
    public void ActionMenu (ActionEvent event){
        Platform.exit();
        System.exit(0);
    }
    //tạo comBox chọn ngôn ngữ
    @FXML
    public ComboBox<String> comboBox;
    //tạo TextFiled
    @FXML
    TextField Word;
    //taoj webView
    @FXML
    WebView webView;
    //Tạo sự kiên Button Translate
    ReadDic readDic = new ReadDic();
    public void ActionButton (ActionEvent event){
        String word = Word.getText();
        webView.getEngine().loadContent(readDic.data.get(word));
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        readDic.ReadDicEV();
        //comboBox
        ObservableList<String> list = FXCollections.observableArrayList("English", "Vietnamese");
        comboBox.setItems(list);
        //Tạo sự kiện khi ấn enter thì serch
        Word.setOnKeyPressed(new EventHandler <KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER)
                {
                    String word = Word.getText();
                    webView.getEngine().loadContent(readDic.data.get(word));
                }
            }
        });
    }
}
