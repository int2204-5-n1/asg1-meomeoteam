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
    private static FXMLLoader mLoader;

    public static FXMLLoader getLoader() {
        return mLoader;
    }

    public static void setLoader(FXMLLoader tempLoader) {
        mLoader = tempLoader;
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
                alert.setContentText("Bạn có chắc là muốn thoát chương trình?");
                ButtonType buttonTypeYES = new ButtonType("YES", ButtonBar.ButtonData.YES);
                ButtonType buttonTypeNO = new ButtonType("NO", ButtonBar.ButtonData.NO);
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
