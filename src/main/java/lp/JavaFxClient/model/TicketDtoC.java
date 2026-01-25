package lp.JavaFxClient.model;

import java.time.LocalDate;
import java.util.List;

public class TicketDtoC {

    private Long idCliente;
    private Long idTicket;
    private String titulo;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Long estado;
    private Long prioridade;
    private Long categoria;
    private String tecnico;
    private List<ComentarioDTO> comentarios;

    public TicketDtoC() {}

    public TicketDtoC(
            Long idCliente,
            Long idTicket,
            String titulo,
            String descricao,
            LocalDate dataInicio,
            LocalDate dataFim,
            Long estado,
            Long categoria,
            Long prioridade,
            String tecnico,
            List<ComentarioDTO> comentarios
    ) {
        this.idCliente=idCliente;
        this.idTicket = idTicket;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.estado = estado;
        this.categoria = categoria;
        this.prioridade= prioridade;
        this.tecnico = tecnico;
        //this.idCliente = idCliente;
        this.comentarios = comentarios;
    }



    // getters & setters

    public Long getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Long idTicket) {
        this.idTicket = idTicket;
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

    public void setComentarios(List<ComentarioDTO> comentarios) {
        this.comentarios = comentarios;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    public Long getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Long prioridade) {
        this.prioridade = prioridade;
    }

    public Long getCategoria() {
        return categoria;
    }

    public void setCategoria(Long categoria) {
        this.categoria = categoria;
    }

    public List<ComentarioDTO> getComentarios() {
        return comentarios;
    }

    public void setComentariosId(List<ComentarioDTO> comentariosId) {
        this.comentarios = comentariosId;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }
}
