package lp.JavaFxClient.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import javafx.beans.property.SimpleStringProperty;
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
import lp.JavaFxClient.model.ComentarioDTO;
import lp.JavaFxClient.services.ApiService;

import java.time.LocalDate;
import java.util.List;

public class ComentarioController {

    @FXML private TableView<ComentarioDTO> tableComentario;
    @FXML private TableColumn<ComentarioDTO, String> tipo;
    @FXML private TableColumn<ComentarioDTO, String> comentarioCol;
    @FXML private TableColumn<ComentarioDTO, LocalDate> dataCol;

    private final ApiService service = new ApiService();
    private Long idTicket;
    private Long idUtilizador;
    private String tipoUtilizador;

    public void configurar(Long idTicket, Long idUtilizador, String tipoUtilizador) {
        this.idTicket = idTicket;
        this.idUtilizador = idUtilizador;
        this.tipoUtilizador = tipoUtilizador;
        tipo.setCellValueFactory(
                cellData -> new SimpleStringProperty(this.tipoUtilizador));
        carregarComentarios();
    }


    @FXML
    public void initialize() {
        comentarioCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMensagem()));
        dataCol.setCellValueFactory(new PropertyValueFactory<>("data"));
    }


    public void carregarComentarios() {
        try {
            List<ComentarioDTO> comentarios;
            if ("CLIENTE".equalsIgnoreCase(tipoUtilizador)) {
                comentarios = service.get("/clientes/" + idUtilizador + "/tickets/" + idTicket + "/listaComentarios",
                        new TypeReference<>() {});
            } else {
                comentarios = service.get("/tecnicos/" + idUtilizador + "/tickets/" + idTicket + "/listaComentarios",
                            new TypeReference<>() {});
            }
            tableComentario.getItems().setAll(comentarios);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void onApagar() {
        ComentarioDTO selecionado = tableComentario.getSelectionModel().getSelectedItem();
        if (selecionado == null) { showError("Selecione um comentário"); return; }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Apagar comentário?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();
        if (confirm.getResult() != ButtonType.YES) return;

        try {
            if ("CLIENTE".equalsIgnoreCase(tipoUtilizador))
                service.delete("/clientes/" + idUtilizador + "/tickets/" + idTicket + "/comentarios/" + selecionado.getIdComentario());
            else
                service.delete("/tecnicos/" + idUtilizador + "/tickets/" + idTicket + "/comentarios/" + selecionado.getIdComentario());
            carregarComentarios();
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    public void onRegistar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp/JavaFxClient/comentario-form-view.fxml"));
            Parent root = loader.load();
            ComentarioFormController controller = loader.getController();
            controller.configurar(idTicket, idUtilizador, tipoUtilizador);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            carregarComentarios();
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }
}
