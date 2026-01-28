package lp.JavaFxClient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComentarioDTO {

    private Long idComentario;
    private String mensagem;
    private LocalDate data;

    public ComentarioDTO() {}

    // GETTERS & SETTERS
    public Long getIdComentario() { return idComentario; }
    public void setIdComentario(Long idComentario) { this.idComentario = idComentario; }
    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
}
