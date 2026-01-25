package lp.JavaFxClient.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lp.JavaFxClient.model.UtilizadorDTO;
import lp.JavaFxClient.services.ApiService;

import java.io.IOException;
import java.util.List;

public class LoginController {

    private final ApiService service = new ApiService();
    private final ObjectMapper mapper = new ObjectMapper();

    @FXML private TextField email;
    @FXML private PasswordField senha;

    @FXML
    private void onLogin() {
        try {
            String user = email.getText();
            String pass = senha.getText();

            if (user.isBlank() || pass.isBlank()) {
                showError("Preencha todos os campos");
                return;
            }

            // Buscar todos os utilizadores
            List<UtilizadorDTO> utilizadores =
                    service.get("/utilizadores", new TypeReference<List<UtilizadorDTO>>() {});

            for (UtilizadorDTO u : utilizadores) {
                if (u.getEmail().equals(user) && u.getSenha().equals(pass)) {

                    if ("CLIENTE".equalsIgnoreCase(u.getTipo())) {
                        abrirViewCliente(u.getId());
                        return;
                    }

                    if ("TECNICO".equalsIgnoreCase(u.getTipo())) {
                        abrirViewTecnico(u.getId());
                        return;
                    }
                }
            }

            showError("Credenciais inválidas");

        } catch (Exception e) {
            showError("Erro no login: " + e.getMessage());
        }
    }

    private void abrirViewCliente(Long id) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp/JavaFxClient/ticket-cliente-view.fxml"));
        Parent root = loader.load();

        TicketClienteController controller = loader.getController();
        controller.setId(id);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Cliente Dashboard");
        stage.show();

        // Fecha a janela de login
        email.getScene().getWindow().hide();
    }

    private void abrirViewTecnico(Long id) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp/JavaFxClient/ticket-tecnico-view.fxml"));
        Parent root = loader.load();

        TicketTecnicoController controller = loader.getController();
        controller.setId(id);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Técnico Dashboard");
        stage.show();

        // Fecha a janela de login
        email.getScene().getWindow().hide();
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText("Login inválido");
        a.setContentText(msg);
        a.showAndWait();
    }
}
