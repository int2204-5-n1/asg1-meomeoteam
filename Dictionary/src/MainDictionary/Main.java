package MainDictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class Main extends Application {
    private static FXMLLoader Loader;

    public static FXMLLoader getLoader() {
        return Loader;
    }

    public static void setLoader(FXMLLoader tempLoader) {
        Loader = tempLoader;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("MainDictionary.fxml"));
            fxmlLoader.load();
            Parent root =fxmlLoader.getRoot();
            setLoader(fxmlLoader);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("Themes.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

            //đóng cửa sổ
            primaryStage.setOnCloseRequest(e ->{
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setContentText("Bạn muốn thoát chương trình?");
                ButtonType buttonTypeYES = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                ButtonType buttonTypeNO = new ButtonType("No", ButtonBar.ButtonData.NO);
                alert.getButtonTypes().setAll(buttonTypeYES,buttonTypeNO);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeYES)
                    primaryStage.close();
                else if (result.get().getButtonData() == ButtonBar.ButtonData.NO)
                    e.consume();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
