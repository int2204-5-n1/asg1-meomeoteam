package MainDictionary;

import Data.ConnectionData;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TextField Word;
    @FXML
    private ListView<String> listView;
    @FXML
    private WebView webView;
    @FXML
    private ComboBox <String> comboBox;

    private String Type = "None";
    private Connection connection=ConnectionData.getConnection();
    private PreparedStatement preparedStatement=null;
    private ResultSet resultSet=null;

    private ObservableList <String> listLanguage = FXCollections.observableArrayList("English - Vietnamese", "Vietnamese - English");
    private ObservableList<String> data=FXCollections.observableArrayList();

    public void ActionSearch(ActionEvent event) throws SQLException {
        String word=Word.getText();
        if (word.equals("")) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Từ của bạn nhập chưa đúng! Xin hãy nhập lại");
            alert.show();
        } else {
            String query="Select * from " + Type + " WHERE word=?";
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1, word);
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                webView.getEngine().loadContent(resultSet.getString("detail"));
            }
        }
    }

    public void show() {
        try {
            String query="Select word from " + Type;
            preparedStatement=connection.prepareStatement(query);
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                data.add(resultSet.getString(1));
            }
            listView.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ActionAdd(ActionEvent event) throws IOException {
        Stage stage=new Stage();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("Add.fxml"));
        Parent View=loader.load();
        Scene scene=new Scene(View);
        stage.setScene(scene);
        stage.show();
    }

    public void ActionEdit(ActionEvent event) throws IOException {
        if (listView.getSelectionModel().getSelectedItem() != null) {
            Stage stage=new Stage();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("Edit.fxml"));
            Parent View=loader.load();
            Scene scene=new Scene(View);

            String choose = listView.getSelectionModel().getSelectedItem();
            ControllerEdit controllerEdit = loader.getController();
            controllerEdit.ChooseWord.setText(choose);
            String query = "Select * from " + Type + " where word=?";
            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, choose);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    controllerEdit.MeanEdit.setText(resultSet.getString("detail"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            stage.setScene(scene);
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Vui lòng chọn từ để sửa!");
            alert.show();
        }
    }
    public void Sound(ActionEvent event){
        String word = listView.getSelectionModel().getSelectedItem();
        if (listView.getSelectionModel().getSelectedItem() != null) {
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            VoiceManager voiceManager=VoiceManager.getInstance();
            Voice voice=voiceManager.getVoice("kevin16");
            voice.allocate();
            voice.speak(word);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Vui lòng chọn từ để thao tác!");
            alert.show();
        }
    }

    public void setLanguage(ActionEvent event){
        if (comboBox.getValue().equals("Vietnamese - English")){
            Type = "V_E";
            data.clear();
            show();
        }
        else if (comboBox.getValue().equals("English - Vietnamese")){
            Type = "E_V";
            data.clear();
            show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //hiện listview
        show();

        //ấn enter dẻ search
        Word.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    if (Word.getText().equals("")) {
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR");
                        alert.setHeaderText("Từ của bạn nhập chưa đúng! Xin hãy nhập lại");
                        alert.show();
                    } else {
                        String query="Select * from " + Type + " WHERE word=?";
                        try {
                            preparedStatement=connection.prepareStatement(query);
                            preparedStatement.setString(1, Word.getText());
                            resultSet=preparedStatement.executeQuery();
                            while (resultSet.next()) {
                                webView.getEngine().loadContent(resultSet.getString("detail"));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        //Click vào từ trên listview để hiện nghĩa
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String query="Select * from " + Type + " where word=?";
                try {
                    preparedStatement=connection.prepareStatement(query);
                    preparedStatement.setString(1, newValue);
                    resultSet=preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        webView.getEngine().loadContent(resultSet.getString("detail"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        //gợi ý khi gõ từ
        FilteredList<String> filteredList=new FilteredList <>(data, event -> true);
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

        //comboxBox
        comboBox.setItems(listLanguage);
    }

    public void AddWord(String wordAdd, String meanAdd) throws SQLException {
        String query="INSERT INTO " + Type + " (word, detail) VALUES(?,?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, wordAdd);
        preparedStatement.setString(2, meanAdd);
        preparedStatement.execute();
        data.clear();
        show();
    }

    public void EditWord(String chooseWord, String wordEdit, String meanEdit) throws SQLException {
        //edit nghĩa
        if(wordEdit.equals("")){
            String query = "delete from " + Type + " where word=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, chooseWord);
            preparedStatement.execute();
            webView.getEngine().loadContent("");

            AddWord(chooseWord, meanEdit);
        }
        //edit word
        else if (!chooseWord.equals(wordEdit)){
            String query = "Select * from " + Type + " where word=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, chooseWord);
            resultSet = preparedStatement.executeQuery();
            String mean=null;
            while (resultSet.next()){
                mean = resultSet.getString("detail");
            }

            query = "delete from " + Type + " where word=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, chooseWord);
            preparedStatement.execute();
            webView.getEngine().loadContent("");

            AddWord(wordEdit, mean);
        }
    }

    public void ActionDelete(ActionEvent event) throws SQLException {
        if (listView.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Notification");
            alert.setHeaderText("Bạn có muốn xóa từ này không?");
            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().addAll(buttonTypeYes,buttonTypeNo);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == buttonTypeYes){
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Notification");
                alert1.setHeaderText("Từ của bạn chọn đã được xóa");
                alert1.show();
                String word=(String) listView.getSelectionModel().getSelectedItem();
                String query="delete from " + Type + " where word=?";
                preparedStatement=connection.prepareStatement(query);
                preparedStatement.setString(1, word);
                preparedStatement.execute();
                data.clear();
                webView.getEngine().loadContent("");
                show();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Vui lòng chọn từ để xóa!");
            alert.show();
        }
    }
}
