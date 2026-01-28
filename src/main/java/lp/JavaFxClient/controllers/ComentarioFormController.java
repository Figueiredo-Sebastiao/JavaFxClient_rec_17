package lp.JavaFxClient.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lp.JavaFxClient.model.ComentarioDTO;
import lp.JavaFxClient.services.ApiService;

public class ComentarioFormController {

    @FXML private Label formTitle;
    @FXML private TextField comentarioTxt;

    private final ApiService service = new ApiService();

    private Long ticketId;
    private Long utilizadorId;
    private String tipoUtilizador; // CLIENTE ou TECNICO

    // =============================
    // SETUP
    // =============================
    public void configurar(Long ticketId, Long utilizadorId, String tipoUtilizador) {
        this.ticketId = ticketId;
        this.utilizadorId = utilizadorId;
        this.tipoUtilizador = tipoUtilizador;
        formTitle.setText("Novo Comentário");
    }

    // =============================
    // SALVAR
    // =============================
    @FXML
    public void onSalvar() {
        try {
            if (comentarioTxt.getText().isBlank()) {
                throw new Exception("Comentário não pode estar vazio");
            }

            ComentarioDTO dto = new ComentarioDTO();
            dto.setTexto(comentarioTxt.getText());

            if (ticketId == null) {
                throw new Exception("Ticket não identificado");
            }
            if ("CLIENTE".equalsIgnoreCase(tipoUtilizador)) {
                service.post(
                        "/clientes/" + utilizadorId + "/tickets/" + ticketId + "/comentarios",
                        dto
                );
            } else {
                service.post(
                        "/tecnicos/" + utilizadorId + "/tickets/" + ticketId + "/comentarios",
                        dto
                );
            }
            System.out.println("ID TICKET = " + ticketId);


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
