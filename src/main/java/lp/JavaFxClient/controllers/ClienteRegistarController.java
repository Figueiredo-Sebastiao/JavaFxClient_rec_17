package lp.JavaFxClient.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lp.JavaFxClient.model.ClienteDTO;
import lp.JavaFxClient.services.ApiService;

public class ClienteRegistarController {

    @FXML private TextField txtNome;
    @FXML private TextField txtEmail;
    @FXML private TextField txtSenha;
    @FXML private TextField txtMorada;
    @FXML private TextField txtTelefone;

    private final ApiService service = new ApiService();

    @FXML
    public void onSalvar() {
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
    }

    @FXML
    public void onCancelar() {
        txtNome.getScene().getWindow().hide();
    }
}
