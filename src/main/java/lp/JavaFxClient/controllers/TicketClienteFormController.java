package lp.JavaFxClient.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import lp.JavaFxClient.model.TicketDtoC;
import lp.JavaFxClient.services.ApiService;

public class TicketClienteFormController {

    @FXML private Label formTitle;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtDescricao;
    @FXML private ChoiceBox<String> cbCategoria;
    @FXML private ChoiceBox<String> cbPrioridade;

    private final ApiService service = new ApiService();

    private Long editingTicketId = null;
    private Long clienteId=2L;

    // =============================
    // INICIALIZAÇÃO
    // =============================
    @FXML
    public void initialize() {
        cbCategoria.getItems().addAll("Hardware", "Software", "Rede");
        cbPrioridade.getItems().addAll("Baixa", "Media", "Alta");
    }

    // =============================
    // REGISTAR
    // =============================
    public void Registar(Long clienteId) {
        this.clienteId = clienteId;
        this.editingTicketId = null;
        formTitle.setText("Registar Ticket");
    }

    // =============================
    // EDITAR
    // =============================
    public void Editar(TicketDtoC ticket) {
        this.editingTicketId = ticket.getIdTicket();
        this.clienteId = ticket.getIdCliente();

        formTitle.setText("Editar Ticket");
        txtTitulo.setText(ticket.getTitulo());
        txtDescricao.setText(ticket.getDescricao());

        cbCategoria.setValue(mapCategoria(ticket.getCategoria()));
        cbPrioridade.setValue(mapPrioridade(ticket.getPrioridade()));
    }

    // =============================
    // SALVAR (POST / PUT)
    // =============================
    @FXML
    public void onSalvar() {
        try {
            if (cbCategoria.getValue() == null || cbPrioridade.getValue() == null)
                throw new Exception("Selecione categoria e prioridade");

            TicketDtoC dto = new TicketDtoC();
            dto.setTitulo(txtTitulo.getText());
            dto.setDescricao(txtDescricao.getText());
            dto.setCategoria(mapCategoriaId(cbCategoria.getValue()));
            dto.setPrioridade(mapPrioridadeId(cbPrioridade.getValue()));

            if (editingTicketId == null) {
                service.post("/clientes/" + 2 + "/tickets", dto);
            } else {
                service.put(
                        "/clientes/" + 2 + "/tickets/" + editingTicketId,
                        dto
                );
            }

            txtTitulo.getScene().getWindow().hide();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    public void onCancelar() {
        txtTitulo.getScene().getWindow().hide();
    }

    // =============================
    // MAPEAMENTOS
    // =============================
    private String mapCategoria(Long id) {
        return switch (id.intValue()) {
            case 1 -> "Hardware";
            case 2 -> "Software";
            case 3 -> "Rede";
            default -> null;
        };
    }

    private Long mapCategoriaId(String nome) {
        return switch (nome) {
            case "Hardware" -> 1L;
            case "Software" -> 2L;
            case "Rede" -> 3L;
            default -> null;
        };
    }

    private String mapPrioridade(Long id) {
        return switch (id.intValue()) {
            case 1 -> "Baixa";
            case 2 -> "Media";
            case 3 -> "Alta";
            default -> null;
        };
    }

    private Long mapPrioridadeId(String nome) {
        return switch (nome) {
            case "Baixa" -> 1L;
            case "Media" -> 2L;
            case "Alta" -> 3L;
            default -> null;
        };
    }
}
