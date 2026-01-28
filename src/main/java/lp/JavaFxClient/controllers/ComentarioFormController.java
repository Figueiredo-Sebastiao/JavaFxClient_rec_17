package lp.JavaFxClient.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lp.JavaFxClient.model.UtilizadorDTO;
import lp.JavaFxClient.services.ApiService;

public class RegistarFormController {

    @FXML private TextField nomeField;
    @FXML private TextField generoField;
    @FXML private TextField emailField;

    private final ApiService service = new ApiService();

    @FXML
    public void onRegistar() {
        try {
            UtilizadorDTO dto = new UtilizadorDTO();
            dto.setNome(nomeField.getText());
            dto.setGenero(generoField.getText());
            dto.setEmail(emailField.getText());

            service.post("/utilizadores/registar", dto);

            new Alert(Alert.AlertType.INFORMATION, "Registo efetuado com sucesso").showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro no registo: " + e.getMessage()).showAndWait();
        }
    }
}
