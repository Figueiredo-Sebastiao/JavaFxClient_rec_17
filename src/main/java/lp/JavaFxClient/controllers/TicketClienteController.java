package lp.JavaFxClient.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lp.JavaFxClient.model.TicketDtoC;
import lp.JavaFxClient.services.ApiService;

import java.time.LocalDate;
import java.util.List;

public class TicketClienteController {

    @FXML private TableView<TicketDtoC> tableTicketsC;
    @FXML private TableColumn<TicketDtoC, Long> idCol;
    @FXML private TableColumn<TicketDtoC, String> tituloCol;
    @FXML private TableColumn<TicketDtoC, String> descricaoCol;
    @FXML private TableColumn<TicketDtoC, String> categoriaCol;
    @FXML private TableColumn<TicketDtoC, String> estadoCol;
    @FXML private TableColumn<TicketDtoC, String> tecnicoCol;
    @FXML private TableColumn<TicketDtoC, String> prioridadeCol;
    @FXML private TableColumn<TicketDtoC, LocalDate> dataInicioCol;
    @FXML private TableColumn<TicketDtoC, LocalDate> dataFim;

    private final ApiService service = new ApiService();
    private long idCliente;

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
        CaregarTickets();
    }

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("idTicket"));
        tituloCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("titulo"));
        descricaoCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("descricao"));
        categoriaCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("categoria"));
        estadoCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("estado"));
        prioridadeCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("prioridade"));
        tecnicoCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("tecnico"));
        dataInicioCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("dataInicio"));
        dataFim.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("dataFim"));
    }

    @FXML
    public void onAtualizar() { CaregarTickets(); }

    @FXML
    public void onComentario() { abrirCaixaComentario(); }

    @FXML
    public void onApagar() {
        TicketDtoC ticketSelec = tableTicketsC.getSelectionModel().getSelectedItem();
        if (ticketSelec == null) { showError("Selecione o ticket"); return; }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Apagar Ticket " + ticketSelec.getTitulo() + "?",
                ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();
        if (confirm.getResult() != ButtonType.YES) return;

        if (ticketSelec.getEstado() == 2) { showError("Nao pode apagar um ticket em processo"); return; }

        service.delete("/clientes/"+ idCliente +"/tickets/"+ ticketSelec.getIdTicket());
        CaregarTickets();
    }

    @FXML
    public void onRegistar() { abrirFormulario(null); }

    @FXML
    public void onEditar() {
        TicketDtoC ticket = tableTicketsC.getSelectionModel().getSelectedItem();
        if (ticket == null) { showError("Selecione um ticket"); return; }
        abrirFormulario(ticket);
    }

    private void CaregarTickets() {
        try {
            List<TicketDtoC> tickets = service.get("/clientes/" + idCliente + "/tickets",
                    new TypeReference<List<TicketDtoC>>() {});
            tableTicketsC.getItems().setAll(tickets);
        } catch (Exception e) {
            showError("Erro ao carregar tickets: " + e.getMessage());
        }
    }

    private void abrirCaixaComentario() {
        try {
            TicketDtoC ticketSelecionado = tableTicketsC.getSelectionModel().getSelectedItem();
            if (ticketSelecionado == null) { showError("Selecione um ticket"); return; }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp/JavaFxClient/comentario-view.fxml"));
            Parent root = loader.load();
            ComentarioController controller = loader.getController();
            controller.configurar(ticketSelecionado.getIdTicket(), idCliente, "CLIENTE");

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle("Comentários");
            stage.setWidth(800);
            stage.setHeight(600);
            stage.centerOnScreen();
            stage.showAndWait();
        } catch (Exception e) {
            showError("Erro ao abrir comentários: " + e.getMessage());
        }
    }

    private void abrirFormulario(TicketDtoC ticket) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp/JavaFxClient/ticket-form-view.fxml"));
            Parent root = loader.load();
            TicketClienteFormController controller = loader.getController();

            if (ticket == null) controller.configurarFormularioParaRegisto(idCliente);
            else controller.configurarFormularioParaEditar(ticket);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle(ticket == null ? "Registar Ticket" : "Editar Ticket");
            stage.showAndWait();

            CaregarTickets();
        } catch (Exception e) {
            showError("Erro ao abrir formulário: " + e.getMessage());
        }
    }

    private void showError(String msg) { new Alert(Alert.AlertType.ERROR, msg).showAndWait(); }
}
