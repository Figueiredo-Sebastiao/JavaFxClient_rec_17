package lp.JavaFxClient.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lp.JavaFxClient.model.TecnicoDTO;
import lp.JavaFxClient.services.ApiService;

import java.io.IOException;

public class TecnicoRegistarController {

    @FXML private TextField txtNome;
    @FXML private TextField txtEmail;
    @FXML private TextField txtSenha;
    @FXML private TextField txtEspecialidade;

    private final ApiService service = new ApiService();

    @FXML
    public void onSalvar() {
        try {
            if (txtNome.getText().isBlank() || txtEmail.getText().isBlank() ||
                    txtSenha.getText().isBlank() || txtEspecialidade.getText().isBlank()) {
                throw new RuntimeException("Preencha todos os campos");
            }

            TecnicoDTO dto = new TecnicoDTO();
            dto.setNome(txtNome.getText());
            dto.setEmail(txtEmail.getText());
            dto.setSenha(txtSenha.getText());
            dto.setEspecialidade(txtEspecialidade.getText());
            dto.setTipo("TECNICO");
            dto.setAutenticado(false);
            service.post("/utilizador/registar/tecnico", dto);

            new Alert(Alert.AlertType.INFORMATION, "Técnico registado com sucesso!").showAndWait();
            txtNome.getScene().getWindow().hide();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }

        try {
            abrirFXML("/lp/JavaFxClient/login-view.fxml", "Registar", 800, 1000);
        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION, "Erro ao Abrir Dashbaod Tecnico").showAndWait();
            txtNome.getScene().getWindow().hide();
        }
    }

    @FXML
    public void onCancelar() {
        txtNome.getScene().getWindow().hide();
    }

    /// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void abrirFXML(String caminho, String titulo, double width, double height) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(titulo);
        stage.setWidth(width);
        stage.setHeight(height);
        stage.centerOnScreen();
        stage.show();
    }
}
