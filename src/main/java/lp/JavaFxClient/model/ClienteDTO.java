package lp.JavaFxClient.model;

public class ClienteDTO {

    private String nome;
    private String email;
    private String senha;
    private String genero;
    private String tipo;
    private boolean autenticado;
    private String morada;
    private int telefone;

    public ClienteDTO() {}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public boolean isAutenticado() { return autenticado; }
    public void setAutenticado(boolean autenticado) { this.autenticado = autenticado; }

    public String getMorada() { return morada; }
    public void setMorada(String morada) { this.morada = morada; }

    public int getTelefone() { return telefone; }
    public void setTelefone(int telefone) { this.telefone = telefone; }
}
