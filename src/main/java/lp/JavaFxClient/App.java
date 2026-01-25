package lp.JavaFxClient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
 
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp/JavaFxClient/ticket-cliente-view.fxml"));
        Scene scene = new Scene(loader.load());

        stage.setTitle("School API Client");
        stage.setScene(scene);

        // Set preferred window size
          // optional

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}


