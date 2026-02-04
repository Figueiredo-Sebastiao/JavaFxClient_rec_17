package lp.JavaFxClient.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lp.JavaFxClient.model.EstadoDTO;
import lp.JavaFxClient.model.TicketDtoT;
import lp.JavaFxClient.services.ApiService;

public class TicketTecnicoFormController {

    @FXML private Label formTitle;
    @FXML private TextField txtTitulo;
    @FXML private ChoiceBox<String> cbEstado;

    private final ApiService service = new ApiService();

    private Long ticketId;
    private Long tecnicoId;

    @FXML
    public void initialize() {
        cbEstado.getItems().add("Resolvido");
        cbEstado.setValue("Resolvido");

        // técnico não edita título
        txtTitulo.setDisable(true);
    }

    public void Editar(TicketDtoT ticket, Long tecnicoId) {
        this.ticketId = ticket.getIdTicket();
        this.tecnicoId = tecnicoId;

        formTitle.setText("Resolver Ticket");
        txtTitulo.setText(ticket.getTitulo());
        cbEstado.setValue("Resolvido");
    }

    @FXML
    public void onSalvar() {
        try {
            if (ticketId == null || tecnicoId == null)
                throw new RuntimeException("Dados inválidos");

            // só envia o ID do estado
            service.put(
                    "/tecnicos/" + tecnicoId + "/tickets/" + ticketId + "/estados",
                    new EstadoDTO(3L) // 3 = Resolvido
            );

            showInfo("Sucesso", "Ticket resolvido com sucesso!");

            Stage stage = (Stage) txtTitulo.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            showError("Erro ao resolver ticket: " + e.getMessage());
        }
    }

    @FXML
    public void onCancelar() {
        ((Stage) txtTitulo.getScene().getWindow()).close();
    }

    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }

    private void showInfo(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
