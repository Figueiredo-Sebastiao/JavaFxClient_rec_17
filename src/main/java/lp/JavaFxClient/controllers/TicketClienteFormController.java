package lp.JavaFxClient.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lp.JavaFxClient.model.TicketDtoC;
import lp.JavaFxClient.services.ApiService;

import java.time.LocalDate;

public class TicketClienteFormController {

    @FXML private Label formTitle;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtDescricao;
    @FXML private ChoiceBox<String> cbCategoria;
    @FXML private ChoiceBox<String> cbPrioridade;

    private final ApiService service = new ApiService();
    private Long editingTicketId = null;
    private Long clienteId;
    private Long estado = 1L;

    @FXML
    public void initialize() {
        cbCategoria.getItems().addAll("Hardware", "Software", "Rede");
        cbPrioridade.getItems().addAll("Baixa", "Media", "Alta");

        cbCategoria.setValue("Hardware");
        cbPrioridade.setValue("Baixa");
    }

    public void Registo(Long clienteId) {
        this.clienteId = clienteId;
        formTitle.setText("Registar Ticket");
    }

    public void Editar(TicketDtoC ticket) {
        this.editingTicketId = ticket.getIdTicket();
       // this.clienteId = clienteId;

        formTitle.setText("Editar Ticket");
        txtTitulo.setText(ticket.getTitulo());
        txtDescricao.setText(ticket.getDescricao());
        cbCategoria.setValue(mapCategoria(ticket.getCategoria()));
        cbPrioridade.setValue(mapPrioridade(ticket.getPrioridade()));
    }

    @FXML
    public void onSalvar() {
        try {
            if (txtTitulo.getText().isEmpty() || txtDescricao.getText().isEmpty())
                throw new Exception("Preencha título e descrição");

            TicketDtoC dto = new TicketDtoC();
            dto.setTitulo(txtTitulo.getText());
            dto.setDescricao(txtDescricao.getText());
            dto.setCategoria(mapCategoriaId(cbCategoria.getValue()));
            dto.setPrioridade(mapPrioridadeId(cbPrioridade.getValue()));
            dto.setDataInicio(LocalDate.now()); // Mantém a data de início

            // 🔹 Se for edição, envia o ID do ticket
            if (editingTicketId == null) {
                dto.setEstado(estado);
                dto.setIdCliente(clienteId);
                service.post("/clientes/" + clienteId + "/tickets", dto);
                showInfo("Sucesso", "Ticket registado com sucesso!");
            } else {
                dto.setIdTicket(editingTicketId);
                dto.setIdCliente(clienteId);

                // 🔹 Verifica se o backend precisa de algum campo extra
                service.put("/clientes/" + clienteId + "/tickets/" + editingTicketId, dto);
                showInfo("Sucesso", "Ticket atualizado com sucesso!");
            }

            Stage stage = (Stage) txtTitulo.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            showError("Erro ao salvar ticket: " + e.getMessage());
        }
    }

    @FXML
    public void onCancelar() {
        Stage stage = (Stage) txtTitulo.getScene().getWindow();
        stage.close();
    }

    private String mapCategoria(Long id) {
        if (id == null) return "Hardware";
        return switch (id.intValue()) {
            case 1 -> "Hardware";
            case 2 -> "Software";
            case 3 -> "Rede";
            default -> "Hardware";
        };
    }

    private Long mapCategoriaId(String nome) {
        return switch (nome) {
            case "Hardware" -> 1L;
            case "Software" -> 2L;
            case "Rede" -> 3L;
            default -> 1L;
        };
    }

    private String mapPrioridade(Long id) {
        if (id == null) return "Baixa";
        return switch (id.intValue()) {
            case 1 -> "Baixa";
            case 2 -> "Media";
            case 3 -> "Alta";
            default -> "Baixa";
        };
    }

    private Long mapPrioridadeId(String nome) {
        return switch (nome) {
            case "Baixa" -> 1L;
            case "Media" -> 2L;
            case "Alta" -> 3L;
            default -> 1L;
        };
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
