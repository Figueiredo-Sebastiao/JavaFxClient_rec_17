package lp.JavaFxClient.model;

public class PrioridadeDTO {

    private Long idPrioridade;
    private String prioridade;


    public PrioridadeDTO(Long idPrioridade, String propriedade) {
        this.idPrioridade=idPrioridade;
        this.prioridade = propriedade;
	}

    public Long getIdPrioridade() {
        return idPrioridade;
    }

    public void setIdPrioridade(Long idPrioridade) {
        this.idPrioridade = idPrioridade;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }
}
