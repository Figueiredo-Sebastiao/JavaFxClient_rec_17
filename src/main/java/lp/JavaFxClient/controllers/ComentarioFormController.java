package lp.JavaFxClient.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lp.JavaFxClient.model.ComentarioDTO;
import lp.JavaFxClient.services.ApiService;

import java.time.LocalDate;

public class ComentarioFormController {

    @FXML private Label formTitle;
    @FXML private TextField comentarioTxt;

    private final ApiService service = new ApiService();

    private Long ticketId;
    private Long utilizadorId;
    private String tipoUtilizador; // CLIENTE ou TECNICO

    public void configurar(Long ticketId, Long utilizadorId, String tipoUtilizador) {
        this.ticketId = ticketId;
        this.utilizadorId = utilizadorId;
        this.tipoUtilizador = tipoUtilizador;
        formTitle.setText("Novo Comentário");
    }

    @FXML
    public void onSalvar() {
        try {
            if (comentarioTxt.getText().isBlank()) {
                throw new Exception("Deixe um Comentário");
            }

            ComentarioDTO dto = new ComentarioDTO();
            dto.setMensagem(comentarioTxt.getText());
            dto.setData(LocalDate.now());

            if (ticketId == null) {
                throw new Exception("Ticket não identificado");
            }
            if ("CLIENTE".equalsIgnoreCase(tipoUtilizador)) {
                service.post("/clientes/" + utilizadorId + "/tickets/" + ticketId + "/comentarios",dto);
            } else {
                service.post("/tecnicos/" + utilizadorId + "/tickets/" + ticketId + "/comentarios",dto );
            }
            //System.out.println("ID TICKET = " + ticketId);


            comentarioTxt.getScene().getWindow().hide();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    public void onCancelar() {
        comentarioTxt.getScene().getWindow().hide();
    }
}
