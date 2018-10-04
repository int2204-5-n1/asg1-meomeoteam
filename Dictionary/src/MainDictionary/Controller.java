package MainDictionary;

import ReadDictionary.ReadDic;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class Controller implements Initializable {

    //tạo TextFiled
    @FXML
    private TextField Word;

    //tạo webView
    @FXML
    private WebView webView;

    //tạo ListView
    @FXML
    private ListView <String> listView;

    ReadDic readDic=new ReadDic();

    //tao su kien Quit Menu
    public void ActionMenu(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    //Tạo sự kiên Button Translate
    public void ActionButton(ActionEvent event) {
        String word=Word.getText();
        webView.getEngine().loadContent(readDic.data.get(word));
    }

    //Tạo sự kiện khi ấn vào word trên listView thì sẽ translate
    @FXML
    private void ClickMouse(MouseEvent event) {
        String word=listView.getSelectionModel().getSelectedItem();
        if (word != null || !word.isEmpty()) {
            webView.getEngine().loadContent(readDic.data.get(word));
            //Word.setText(word);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        readDic.ReadDicEV();

        //Tạo sự kiện khi ấn enter thì serch
        Word.setOnKeyPressed(new EventHandler <KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    String word=Word.getText();
                    webView.getEngine().loadContent(readDic.data.get(word));
                }
            }
        });

        //Tạo ra ListView
        ObservableList <String> data=FXCollections.observableArrayList(readDic.keys);
        listView.setItems(data);

        //Hiển thị suggestion khi gõ từ
        FilteredList <String> filteredList=new FilteredList <>(data, event -> true);
        listView.setItems(filteredList);
        Word.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(event -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseSearch=newValue.toLowerCase();
                if (event.toLowerCase().startsWith(lowerCaseSearch))
                    return true;
                else return false;
            });
            listView.setItems(filteredList);
        });
    }
}
