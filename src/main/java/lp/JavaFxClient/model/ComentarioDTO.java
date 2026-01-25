package lp.JavaFxClient.model;

import java.time.LocalDate;

public class ComentarioDTO {


    //private String nome;
    private String texto;
    private LocalDate data;


    public ComentarioDTO() {
    }

    public ComentarioDTO(String texto, LocalDate data) {
        //   this.nome=nome;
        this.texto = texto;
        this.data = data;

    }


    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

}
