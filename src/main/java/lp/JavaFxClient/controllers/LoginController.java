package lp.JavaFxClient.controllers;

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

public class LoginController {

    private final ApiService service = new ApiService();

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

            UtilizadorDTO dto = new UtilizadorDTO();
            dto.setEmail(user);
            dto.setSenha(pass);

            UtilizadorDTO u = service.post("/utilizador/login", dto, UtilizadorDTO.class);

            if (u.getTipo() == null) {
                throw new Exception("Tipo de utilizador inválido");
            }

            String tipo = u.getTipo().toUpperCase();

            switch (tipo) {
                case "CLIENTE" -> AbrirViewCliente(u.getId());
                case "TECNICO" -> AbrirViewTecnico(u.getId());
                default -> showError("Tipo de utilizador desconhecido: " + u.getTipo());
            }

        } catch (Exception e) {
            showError("Erro no login:\n" + e.getMessage());
        }
    }

    @FXML
    public void onRegistar() {
        try {
            abrirFXML("/lp/JavaFxClient/SelecionarRegistarView.fxml", "Registar", 800, 600);
        } catch (Exception e) {
            showError("Erro ao abrir Registo:\n" + e.getMessage());
        }
    }

    private void AbrirViewCliente(Long id) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/lp/JavaFxClient/ticket-cliente-view.fxml")
            );
            Parent root = loader.load();
            TicketClienteController controller = loader.getController();
            controller.setIdCliente(id);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Cliente Dashboard");
            stage.show();
            email.getScene().getWindow().hide();


            fecharLogin();
        } catch (IOException e) {
            showError("Erro ao abrir o Dashboard do Cliente.\nVerifique se o FXML existe em '/lp/JavaFxClient/ticket-cliente-view.fxml'\n" + e.getMessage());
        }
    }

    private void AbrirViewTecnico(Long id) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp/JavaFxClient/ticket-tecnico-view.fxml"));
            Parent root = loader.load();

            lp.JavaFxClient.controllers.TicketTecnicoController controller = loader.getController();
            controller.setIdTecnico(id);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Técnico Dashboard");
            stage.setWidth(800);
            stage.setHeight(600);
            stage.centerOnScreen();
            stage.show();

            fecharLogin();
        } catch (IOException e) {
            showError("Erro ao abrir o Dashboard do Técnico.\nVerifique se o FXML existe em '/lp/JavaFxClient/ticket-tecnico-view.fxml'\n" + e.getMessage());
        }
    }

    // Método genérico para abrir qualquer FXML
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

        fecharLogin();
    }

    // Fecha a janela de login atual
    private void fecharLogin() {
        if (email.getScene() != null && email.getScene().getWindow() != null) {
            email.getScene().getWindow().hide();
        }
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg);
        a.setHeaderText("Erro");
        a.showAndWait();
    }
}
