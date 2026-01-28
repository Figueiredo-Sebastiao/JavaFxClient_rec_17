package lp.JavaFxClient.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SelecionarRegistarController {

    @FXML private Button btnCliente;
    @FXML private Button btnTecnico;

    @FXML
    public void onRegistarCliente() {
        abrirFormulario("/lp/JavaFxClient/ClienteRegistarView.fxml");
    }

    @FXML
    public void onRegistarTecnico() {
        abrirFormulario("/lp/JavaFxClient/TecnicoRegistarView.fxml");
    }

    private void abrirFormulario(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Registo");
            stage.setWidth(600);
            stage.setHeight(500);
            stage.show();
            btnCliente.getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
