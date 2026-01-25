package lp.JavaFxClient.model;

import java.time.LocalDate;
import java.util.List;

public class TicketDtoT {

        private Long idTicket;
        private String cliente;
        private String titulo;
        private String descricao;
        private LocalDate dataInicio;
        private LocalDate dataFim;
        private String categoria;
        private String prioridade;
        private String estado;
        private List<ComentarioDTO> comentarios;


    public TicketDtoT( String cliente, String titulo, String descricao, LocalDate dataInicio, LocalDate dataFim, String categoria, String prioridade, String estado, List<ComentarioDTO> comentarios) {
       // this.idTicket = idTicket;
        this.titulo = titulo;
        this.cliente = cliente;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.categoria = categoria;
        this.prioridade = prioridade;
        this.estado = estado;
        this.comentarios = comentarios;
    }



    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<ComentarioDTO> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioDTO> comentarios) {
        this.comentarios = comentarios;
    }

    public Long getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Long idTicket) {
        this.idTicket = idTicket;
    }
}
