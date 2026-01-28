package lp.JavaFxClient.model;

public class EstadoDTO {
    private Long idEstado;
    private String nome;

    public EstadoDTO() {}

    public EstadoDTO(Long id, String nome) {
        this.idEstado = id;
        this.nome = nome;
    }

    public Long getIdEstado() { return idEstado; }
    public void setIdEstado(Long idEstado) { this.idEstado = idEstado; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public String toString() {
        return nome; // Isso faz aparecer o nome no ChoiceBox
    }
}
