package lp.JavaFxClient.model;


public class CategoriaDTO {


    private Long idCategoria;
    private String categoria;
    
    
// Construtor
    public CategoriaDTO(Long idCategoria, String categoria) {
		this.idCategoria = idCategoria;
		this.categoria=categoria;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
}
