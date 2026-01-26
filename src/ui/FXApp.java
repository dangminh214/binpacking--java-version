package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Load FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../main.fxml"));
        Scene scene = new Scene(loader.load(), 1144, 668); // width x height

        stage.setTitle("Number Input App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
