package br.com.sneacker.sneackerjava.dto;

import jakarta.validation.constraints.*;

public class SneakerRequest {
    @NotBlank(message = "O nome do sneacker é obrigatório!")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 a 100 caracteres")
    private String nome;
    @NotBlank(message = "A marca do sneacker é obrigatória!")
    @Size(min = 2, max = 100, message = "A marca deve ter entre 2 a 100 caracteres")
    private String marca;
    @NotNull(message = "O preço é obrigatório!")
    @DecimalMin(value = "0.0", inclusive = true, message = "O preço mínimo permitido é 0.0")
    private Double preco;
    private String imagem;
    @NotNull(message = "O check é obrigatório!")
    private Boolean adquirido;
    private String nomeMusica;
    private String emailUsuario;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Boolean getAdquirido() {
        return adquirido;
    }

    public void setAdquirido(Boolean adquirido) {
        this.adquirido = adquirido;
    }

    public String getNomeMusica() {
        return nomeMusica;
    }

    public void setNomeMusica(String nomeMusica) {
        this.nomeMusica = nomeMusica;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
}
