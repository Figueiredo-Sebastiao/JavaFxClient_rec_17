package lp.JavaFxClient.controllers;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import lp.JavaFxClient.model.TicketDtoC;
import lp.JavaFxClient.services.ApiService;

import java.time.LocalDate;
import java.util.List;


public class TicketListController {


    @FXML
    private TableView<TicketDtoC> tableTicketsC;
    @FXML private TableColumn<TicketDtoC, Long> idCol;
    @FXML private TableColumn<TicketDtoC, String> tituloCol;
    @FXML private TableColumn<TicketDtoC, String> descricaoCol;
    @FXML private TableColumn<TicketDtoC, String> categoriaCol;
    @FXML private TableColumn<TicketDtoC, String> estadoCol;
    @FXML private TableColumn<TicketDtoC, String> prioridadeCol;
    @FXML private TableColumn<TicketDtoC, LocalDate> dataInicioCol;
    @FXML private TableColumn<TicketDtoC, LocalDate> dataFim;
    private final ApiService service = new ApiService();
    private final ObjectMapper mapper = new ObjectMapper();
    private long id;

    // Getters and Setters


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("idTicket"));
        tituloCol.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        descricaoCol.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        categoriaCol.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        estadoCol.setCellValueFactory(new PropertyValueFactory<>("estado"));
        prioridadeCol.setCellValueFactory(new PropertyValueFactory<>("prioridade"));
        dataInicioCol.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        dataFim.setCellValueFactory(new PropertyValueFactory<>("dataFim"));
        System.out.println("IDCol = " + idCol);
        CaregarTickets();
    }
    @FXML
    public void onAtualizar() {
        CaregarTickets();
    }


    // Apagar
    @FXML
    public void onApagar() {
        TicketDtoC ticketSelec = tableTicketsC.getSelectionModel().getSelectedItem();
        if (ticketSelec == null) {
            showError("Selecione o ticket");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Apagar Ticket " + ticketSelec.getTitulo() + "?",
                ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();
        if (confirm.getResult() != ButtonType.YES) return;
        if (ticketSelec.getEstado()==2){
            showError("Nao pode apagar um ticket em processo");
        }
        service.delete("/clientes/"+id+"/tickets/"+ ticketSelec.getIdTicket());
        CaregarTickets();
    }

    // RIGISTAR
    @FXML
    public void onAdicionar() {

        TicketDtoC ticketSelec = tableTicketsC.getSelectionModel().getSelectedItem();
        if (ticketSelec == null) {
            showError("Selecione o ticket");
            return;
        }
        Long idTicket=ticketSelec.getIdTicket();
        service.get("/"+id+"/tickets/"+idTicket);
    }






    /// /////////////////////////METODOS//////////////////////////////////////////
    private void CaregarTickets() {
        try {
            List<TicketDtoC> tickets = service.get("/clientes/tickets",new TypeReference<List<TicketDtoC>>() {});
            tableTicketsC.getItems().setAll(tickets);

        } catch (Exception e) {
            showError("Erro ao carregar tickets: " + e.getMessage());
        }
    }

    // Metodo Formulario
    private void abrirFormulario(TicketDtoC ticket) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/lp/JavaFxClient/ticket-form-view.fxml")
            );

            Parent root = loader.load();
            TicketClienteFormController controller = loader.getController();

            if (ticket == null) {
                controller.Registar(id); // id = clienteId
            } else {
                controller.Editar(ticket);
            }

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

    // TRATAMENTO DE ERRO
    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg);
        a.showAndWait();
    }

    // TRATAMENTO DE INFORMSCSO
    private void showInfo(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg);
        a.setTitle(title);
        a.showAndWait();
    }

}
