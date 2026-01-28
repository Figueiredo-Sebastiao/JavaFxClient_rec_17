package lp.JavaFxClient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketDtoT {

    @JsonProperty("idTicket") // ou "id_ticket" conforme o backend
    private Long idTicket;

    @JsonProperty("titulo")
    private String titulo;

    @JsonProperty("descricao")
    private String descricao;

    @JsonProperty("categoria")
    private String categoria;

    @JsonProperty("prioridade")
    private String prioridade;

    @JsonProperty("estado")
    private String estado;

    @JsonProperty("cliente")
    private String cliente;

    @JsonProperty("dataInicio")
    private LocalDate dataInicio;

    @JsonProperty("dataFim")
    private LocalDate dataFim;


    public TicketDtoT() {}

    // ---------------- GETTERS ----------------

    public Long getIdTicket() { return idTicket; }
    public String getCliente() { return cliente; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public String getCategoria() { return categoria; }
    public String getEstado() { return estado; }
    public String getPrioridade() { return prioridade; }
    public LocalDate getDataInicio() { return dataInicio; }
    public LocalDate getDataFim() { return dataFim; }

    // ---------------- SETTERS NORMAIS ----------------

    public void setIdTicket(Long idTicket) { this.idTicket = idTicket; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }

    // ---------------- SETTERS INTELIGENTES (FIX FINAL) ----------------

    @JsonSetter("categoria")
    public void setCategoria(JsonNode node) {
        if (node.isTextual()) {
            this.categoria = node.asText();
        } else if (node.has("categoria")) {
            this.categoria = node.get("categoria").asText();
        }
    }

    @JsonSetter("estado")
    public void setEstado(JsonNode node) {
        if (node.isTextual()) {
            this.estado = node.asText();
        } else if (node.has("estado")) {
            this.estado = node.get("estado").asText();
        }
    }

    @JsonSetter("prioridade")
    public void setPrioridade(JsonNode node) {
        if (node.isTextual()) {
            this.prioridade = node.asText();
        } else if (node.has("prioridade")) {
            this.prioridade = node.get("prioridade").asText();
        }
    }
}
