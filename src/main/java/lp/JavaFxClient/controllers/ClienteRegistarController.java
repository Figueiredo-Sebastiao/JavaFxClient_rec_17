package lp.JavaFxClient.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lp.JavaFxClient.model.ClienteDTO;
import lp.JavaFxClient.services.ApiService;

import java.io.IOException;

public class ClienteRegistarController {

    @FXML private TextField txtNome;
    @FXML private TextField txtEmail;
    @FXML private TextField txtSenha;
    @FXML private TextField txtMorada;
    @FXML private TextField txtTelefone;


    private final ApiService service = new ApiService();

    @FXML
    public void onSalvar() throws IOException {
        try {
            if (txtNome.getText().isBlank() || txtEmail.getText().isBlank() ||
                    txtSenha.getText().isBlank() || txtMorada.getText().isBlank() || txtTelefone.getText().isBlank()) {
                throw new RuntimeException("Preencha todos os campos");
            }

            ClienteDTO dto = new ClienteDTO();
            dto.setNome(txtNome.getText());
            dto.setEmail(txtEmail.getText());
            dto.setSenha(txtSenha.getText());
            dto.setMorada(txtMorada.getText());
            dto.setTelefone(Integer.parseInt(txtTelefone.getText()));
            dto.setTipo("CLIENTE");
            dto.setAutenticado(false);

            service.post("/utilizador/registar/cliente", dto);

            new Alert(Alert.AlertType.INFORMATION, "Cliente registado com sucesso!").showAndWait();
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
