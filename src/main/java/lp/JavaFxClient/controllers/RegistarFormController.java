package lp.JavaFxClient.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import lp.JavaFxClient.model.UtilizadorDTO;
import lp.JavaFxClient.services.ApiService;

public class RegistarFormController {

    @FXML private TextField txtNome;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtSenha;
    @FXML private ChoiceBox<String> cbGenero;
    @FXML private ChoiceBox<String> cbTipo;

    private final ApiService service = new ApiService();

    @FXML
    public void initialize() {
        cbGenero.getItems().addAll("Masculino", "Feminino", "Outro");
        cbTipo.getItems().addAll("CLIENTE", "TECNICO");
    }

    @FXML
    public void onSalvar() {
        try {
            if (txtNome.getText().isBlank() || txtEmail.getText().isBlank() || txtSenha.getText().isBlank())
                throw new RuntimeException("Campos obrigatórios");

            UtilizadorDTO u = new UtilizadorDTO();
            u.setNome(txtNome.getText());
            u.setEmail(txtEmail.getText());
            u.setSenha(txtSenha.getText());
            u.setGenero(cbGenero.getValue());
            u.setTipo(cbTipo.getValue());
            u.setAutenticado(false);

            service.post("/utilizadores/registar", u);

            new Alert(Alert.AlertType.INFORMATION, "Registado com sucesso").showAndWait();
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
