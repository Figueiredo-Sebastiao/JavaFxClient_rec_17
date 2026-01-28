package lp.JavaFxClient.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import lp.JavaFxClient.model.TicketDtoC;
import lp.JavaFxClient.model.TicketDtoT;
import lp.JavaFxClient.services.ApiService;

import java.util.List;

public class TicketListController {

    @FXML private TableView<TicketDtoC> tableClientes;
    @FXML private TableView<TicketDtoT> tableTecnicos;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private final ApiService service = new ApiService();

    @FXML
    public void initialize() {
        carregarTicketsClientes();
        carregarTicketsTecnicos();
    }

    private void carregarTicketsClientes() {
        try {
            List<TicketDtoC> ticketsC = service.get("/clientes/todosTickets",
                    new TypeReference<List<TicketDtoC>>() {});
            tableClientes.getItems().setAll(ticketsC);
        } catch (Exception e) {
            showError("Erro ao carregar tickets clientes: " + e.getMessage());
        }
    }

    private void carregarTicketsTecnicos() {
        try {
            List<TicketDtoT> ticketsT = service.get("/tecnicos/todosTickets",
                    new TypeReference<List<TicketDtoT>>() {});
            tableTecnicos.getItems().setAll(ticketsT);
        } catch (Exception e) {
            showError("Erro ao carregar tickets técnicos: " + e.getMessage());
        }
    }

    @FXML
    public void onApagarCliente() {
        TicketDtoC ticket = tableClientes.getSelectionModel().getSelectedItem();
        if (ticket == null) { showError("Selecione um ticket"); return; }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Apagar Ticket " + ticket.getTitulo() + "?",
                ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();
        if (confirm.getResult() != ButtonType.YES) return;

        service.delete("/clientes/" + ticket.getIdCliente() + "/tickets/" + ticket.getIdTicket());
        carregarTicketsClientes();
    }

    @FXML
    public void onApagarTecnico() {
        TicketDtoT ticket = tableTecnicos.getSelectionModel().getSelectedItem();
        if (ticket == null) { showError("Selecione um ticket"); return; }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Apagar Ticket " + ticket.getTitulo() + "?",
                ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();
        if (confirm.getResult() != ButtonType.YES) return;

        service.delete("/tecnicos/" + ticket.getIdTicket() + "/tickets/" + ticket.getIdTicket());
        carregarTicketsTecnicos();
    }

    private void showError(String msg) { new Alert(Alert.AlertType.ERROR, msg).showAndWait(); }
}
