package lp.JavaFxClient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComentarioDTO {

    private Long idComentario;
    private String texto;
    private LocalDate data;

    public ComentarioDTO() {}

    // GETTERS & SETTERS
    public Long getIdComentario() { return idComentario; }
    public void setIdComentario(Long idComentario) { this.idComentario = idComentario; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
}
