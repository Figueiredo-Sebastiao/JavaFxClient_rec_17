package lp.JavaFxClient.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lp.JavaFxClient.model.TicketDtoC;
import lp.JavaFxClient.services.ApiService;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class TicketClienteController implements Initializable {

    @FXML private TableView<TicketDtoC> tableTicketsC;
    @FXML private TableColumn<TicketDtoC, Long> idCol;
    @FXML private TableColumn<TicketDtoC, String> tituloCol;
    @FXML private TableColumn<TicketDtoC, String> descricaoCol;
    @FXML private TableColumn<TicketDtoC, String> categoriaCol;
    @FXML private TableColumn<TicketDtoC, String> estadoCol;
    @FXML private TableColumn<TicketDtoC, String> tecnicoCol;
    @FXML private TableColumn<TicketDtoC, String> prioridadeCol;
    @FXML private TableColumn<TicketDtoC, LocalDate> dataInicioCol;
    @FXML private TableColumn<TicketDtoC, LocalDate> dataFimCol;

    private final ApiService service = new ApiService();
    private long idCliente;

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
        carregarTickets();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("idTicket"));
        tituloCol.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        descricaoCol.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        categoriaCol.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        estadoCol.setCellValueFactory(new PropertyValueFactory<>("estado"));
        prioridadeCol.setCellValueFactory(new PropertyValueFactory<>("prioridade"));
        tecnicoCol.setCellValueFactory(new PropertyValueFactory<>("tecnico"));
        dataInicioCol.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        dataFimCol.setCellValueFactory(new PropertyValueFactory<>("dataFim"));
    }

    @FXML public void onAtualizar() { carregarTickets(); }
    @FXML public void onRegistar() { abrirFormulario(null); }
    @FXML public void onEditar() {
        TicketDtoC t = tableTicketsC.getSelectionModel().getSelectedItem();
        if (t == null) erro("Selecione um ticket");
        else abrirFormulario(t);
    }

    @FXML
    public void onApagar() {
        TicketDtoC t = tableTicketsC.getSelectionModel().getSelectedItem();
        if (t == null) { erro("Selecione um ticket"); return; }
        service.delete("/clientes/" + idCliente + "/tickets/" + t.getIdTicket());
        carregarTickets();
    }

    @FXML
    public void onComentario() {
        try {
            TicketDtoC ticketSelecionado = tableTicketsC.getSelectionModel().getSelectedItem();
            if (ticketSelecionado == null) { erro("Selecione um ticket"); return; }


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp/JavaFxClient/comentario-view.fxml"));
            Parent root = loader.load();
            ComentarioController controller= loader.getController();
            controller.configurar(ticketSelecionado.getIdTicket(),idCliente, "CLIENTE");

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle("Comentários");
            stage.showAndWait();
        } catch (Exception e) {
            erro(e.getMessage());
        }
    }

    private void carregarTickets() {
        try {
            List<TicketDtoC> tickets = service.get(
                    "/clientes/" + idCliente + "/tickets",
                    new TypeReference<List<TicketDtoC>>() {}
            );
            tableTicketsC.getItems().setAll(tickets);
        } catch (Exception e) {
            erro(e.getMessage());
        }
    }

    private void abrirFormulario(TicketDtoC ticket) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/lp/JavaFxClient/ticket-form-view.fxml"));

            Parent root = loader.load();
            TicketClienteFormController controller = loader.getController();
            if (ticket == null) {
                controller.Registo(idCliente);
            } else {
                controller.Editar(ticket);
            }

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            carregarTickets(); // 🔹 atualiza a tabela após fechar o formulário
        } catch (Exception e) {
            erro(e.getMessage());
        }
    }



    private void erro(String m) {
        new Alert(Alert.AlertType.ERROR, m).showAndWait();
    }
}
