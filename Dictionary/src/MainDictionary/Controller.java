package MainDictionary;

import ReadDictionary.ReadDic;
import com.gtranslate.Audio;
import com.gtranslate.Language;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javazoom.jl.decoder.JavaLayerException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.Key;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {
    public int index = 0;
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

    /*//tao su kien Quit Menu
    public void ActionMenu(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
    */
    //Tạo sự kiên Button Translate
    public void ActionButton(ActionEvent event) {
        String word=Word.getText();
        webView.getEngine().loadContent(readDic.data.get(word));
    }

    //Tạo sự kiên cho Add Word
    public void Add (ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ButtonAdd.fxml"));
        Parent View = loader.load();
        Scene scene = new Scene(View);
        stage.setScene(scene);
        stage.show();
    }

    //Tạo sự kiên cho Edit Word
    public void Edit (ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ButtonEdit.fxml"));
        Parent View = loader.load();
        Scene scene = new Scene(View);
        stage.setScene(scene);
        stage.show();
    }

    //Tạo sự kiện khi ấn vào word trên listView thì sẽ translate
    @FXML
    public void ClickMouse(MouseEvent event) {
        String word=listView.getSelectionModel().getSelectedItem();
        if (word != null || !word.isEmpty()) {
            webView.getEngine().loadContent(readDic.data.get(word));
            //Word.setText(word);
        }
    }

    //Tạo sự kiện khi ấn vào button sound, cách phát âm
    public void ActionSound(ActionEvent event) {
            String word=listView.getSelectionModel().getSelectedItem();
            Audio audio=Audio.getInstance();
            InputStream sound= null;
        try {
            sound = audio.getAudio(word,Language.ENGLISH);
        } catch (IOException e) {
            Logger.getLogger(JavaFX_TextToSpeech.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            audio.play(sound);
        } catch (JavaLayerException e) {
            Logger.getLogger(JavaFX_TextToSpeech.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        readDic.ReadDicEV();

        //Tạo sự kiện khi serch
        Word.setOnKeyPressed(new EventHandler <KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                // sự kiện khi nhấn enter
                if (event.getCode() == KeyCode.ENTER) {
                    String word=Word.getText();
                    webView.getEngine().loadContent(readDic.data.get(word));
                }
                //sự kiện khi ấn nút DOWN
                if (event.getCode() == KeyCode.DOWN){
                    listView.getSelectionModel().select(index); // chọn ví trí để bắt đầu
                    listView.getFocusModel().focus(index);
                    listView.scrollTo(index);
                    index++;
                    String word=listView.getSelectionModel().getSelectedItem();
                    webView.getEngine().loadContent(readDic.data.get(word));
                }
                //sự kiện khi ấn nút UP
                else if (event.getCode() == KeyCode.UP){
                    index--;
                    listView.getSelectionModel().select(index); // chọn ví trí để bắt đầu
                    listView.getFocusModel().focus(index);
                    listView.scrollTo(index);
                    if (index < 0)
                        index = 0;
                    String word=listView.getSelectionModel().getSelectedItem();
                    webView.getEngine().loadContent(readDic.data.get(word));
                }
                else index=0;
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

    private class JavaFX_TextToSpeech {
    }
}
