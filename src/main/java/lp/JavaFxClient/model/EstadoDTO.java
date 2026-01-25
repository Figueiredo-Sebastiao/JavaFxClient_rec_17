package lp.JavaFxClient.model;


public class EstadoDTO {


    private Long idEstado;
    private String estado;


    public EstadoDTO(Long idEstado, String estado){
        this.idEstado=idEstado;
        this.estado=estado;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
