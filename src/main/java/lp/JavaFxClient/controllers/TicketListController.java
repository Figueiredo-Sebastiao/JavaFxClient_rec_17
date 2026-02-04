package lp.JavaFxClient.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lp.JavaFxClient.model.TicketDtoT;
import lp.JavaFxClient.services.ApiService;

import java.time.LocalDate;
import java.net.URL;
import java.util.List;

public class TicketListController {

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
    private long tecnicoID;

    public long getTecnicoID() {
        return tecnicoID;
    }

    public void setTecnicoID(long tecnicoID) {
        this.tecnicoID = tecnicoID;
        CarregarTickets();
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

        CarregarTickets();
    }

    @FXML
    public void onAtualizar() {
        CarregarTickets();
    }


    @FXML
    public void onSalvar() {
        try {
            TicketDtoT ticketSelec = tabelaTicketT.getSelectionModel().getSelectedItem();
            if (ticketSelec == null) {
                showError("Selecione o ticket");
                return;
            }

            if (tecnicoID == 0) {
                showError("ID do técnico não definido");
                return;
            }

            Long idTicket = ticketSelec.getIdTicket();

            // 🔹 POST sem retorno
            service.post("/tecnicos/" + tecnicoID + "/tickets/" + idTicket);

            showInfo(
                    "Sucesso",
                    "Ticket associado ao técnico com sucesso!"
            );

            CarregarTickets(); // opcional

        } catch (Exception e) {
            e.printStackTrace();
            showError("Erro ao salvar ticket:\n" + e.getMessage());
        }
    }


    /// /////////////////////////METODOS//////////////////////////////////////////
    private void CarregarTickets() {
        try {
            List<TicketDtoT> tickets = service.get("/tickets", new TypeReference<List<TicketDtoT>>() {});
            tabelaTicketT.getItems().setAll(tickets);
        } catch (Exception e) {
            showError("Erro ao carregar tickets: " + e.getMessage());
        }
    }

    // TRATAMENTO DE ERRO
    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg);
        a.showAndWait();
    }

    // TRATAMENTO DE INFORMAÇÃO
    private void showInfo(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg);
        a.setTitle(title);
        a.showAndWait();
    }

    // Se precisares de abrir formulário (igual ao TicketTecnicoController)
    private void abrirFormularioTicket() {
        try {
            URL fxmlURL = getClass().getResource("/lp/JavaFxClient/ticket-lista-view.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle("Tickets");
            stage.setWidth(1000);
            stage.setHeight(800);
            stage.centerOnScreen();
            stage.showAndWait();

            CarregarTickets();
        } catch (Exception e) {
            showError("Erro ao abrir formulário: \n" + e.getMessage());
        }
    }
}
