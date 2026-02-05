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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lp.JavaFxClient.model.EstadoDTO;
import lp.JavaFxClient.model.TicketDtoC;
import lp.JavaFxClient.model.TicketDtoT;
import lp.JavaFxClient.services.ApiService;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;

public class TicketTecnicoController {

    @FXML private TableView<TicketDtoT> tabelaTicketT;
    @FXML private TableColumn<TicketDtoT, Long> idCol;
    @FXML private TableColumn<TicketDtoT, String> tituloCol;
    @FXML private TableColumn<TicketDtoT, String> descricaoCol;
    @FXML private TableColumn<TicketDtoT, String> categoriaCol;
    @FXML private TableColumn<TicketDtoT, String> estadoCol;
    @FXML private TableColumn<TicketDtoT, String> prioridadeCol;
    @FXML private TableColumn<TicketDtoT, String> clienteCol;
    @FXML private TableColumn<TicketDtoT, LocalDate> dataInicioCol;
    @FXML private TableColumn<TicketDtoT, LocalDate> dataFimCol;

    private final ApiService service = new ApiService();
    private long idTecnico;

    public void setIdTecnico(long idTecnico) {
        this.idTecnico = idTecnico;
        carregarTickets();
    }

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("idTicket"));
        tituloCol.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        descricaoCol.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        categoriaCol.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        estadoCol.setCellValueFactory(new PropertyValueFactory<>("estado"));
        prioridadeCol.setCellValueFactory(new PropertyValueFactory<>("prioridade"));
        clienteCol.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        dataInicioCol.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        dataFimCol.setCellValueFactory(new PropertyValueFactory<>("dataFim"));
    }

    @FXML
    public void onAtualizar() { carregarTickets(); }

    @FXML
    public void onAdicionar() { FormularioAdicionar(); }

    @FXML
    public void onEditar() {
        TicketDtoT ticket = tabelaTicketT.getSelectionModel().getSelectedItem();
        if (ticket == null) { showError("Selecione um ticket"); return; }
        FormularioEditar(ticket);
    }

    @FXML
    public void onRemove() {
        TicketDtoT ticket = tabelaTicketT.getSelectionModel().getSelectedItem();
        if (ticket == null) { showError("Selecione um ticket"); return; }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,"Remover Ticket " + ticket.getTitulo() + "?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();
        if (confirm.getResult() != ButtonType.YES) return;

        service.delete("/tecnicos/" + idTecnico + "/tickets/" + ticket.getIdTicket());
        carregarTickets();
    }

    public void onComentario() { abrirCaixaComentario(); }

    private void carregarTickets() {
        try {
            List<TicketDtoT> tickets = service.get("/tecnicos/" + idTecnico + "/tickets",
                    new TypeReference<List<TicketDtoT>>() {});
            tabelaTicketT.getItems().setAll(tickets);
        } catch (Exception e) {
            showError("Erro ao carregar tickets: " + e.getMessage());
        }
    }

    private void FormularioAdicionar() {
        try {
            URL fxmlURL = getClass().getResource("/lp/JavaFxClient/ticket-lista-view.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Parent root = loader.load();
            TicketListController controller = loader.getController();
            controller.setTecnicoID(idTecnico);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle("Tickets");
            stage.setWidth(800);
            stage.setHeight(1000);
            stage.centerOnScreen();
            stage.showAndWait();

            carregarTickets();
        } catch (Exception e) {
            showError("Erro ao abrir formulário: \n" + e.getMessage());
        }
    }
    private void FormularioEditar(TicketDtoT ticket) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/lp/JavaFxClient/ticket-form-Tecnico-view.fxml"));

            Parent root = loader.load();
            TicketTecnicoFormController controller = loader.getController();

            if (ticket != null) {
                controller.Editar(ticket, idTecnico);
            }

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setWidth(1000);
            stage.setHeight(800);
            stage.showAndWait();

            carregarTickets();
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    public void abrirCaixaComentario() {
        try {
            TicketDtoT ticketSelecionado = tabelaTicketT.getSelectionModel().getSelectedItem();
            if (ticketSelecionado == null) { showError("Selecione um ticket"); return; }
            if (idTecnico <= 0) { showError("Técnico não identificado"); return; }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp/JavaFxClient/comentario-view.fxml"));
            Parent root = loader.load();
            ComentarioController controller= loader.getController();
            controller.configurar(ticketSelecionado.getIdTicket(), idTecnico,"TECNICO");

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


    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }
}
