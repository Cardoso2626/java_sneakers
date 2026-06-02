package br.com.sneacker.sneackerjava.model;


import jakarta.persistence.*;

@Entity
@Table(name = "tb_sneaker")
public class Sneaker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sneaker_id")
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "marca")
    private String marca;
    @Column(name = "preco", columnDefinition = "numeric")
    private Double preco;
    @Column(name = "imagem")
    private String imagem;
    @Column(name = "adquirido")
    private Boolean adquirido;
    @ManyToOne
    @JoinColumn(name = "musica_id")
    private Musica musica;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


    public Sneaker(){

    }


    public Sneaker(Long id, String nome, String marca, Double preco, String imagem, Boolean adquirido, Musica musica, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.preco = preco;
        this.imagem = imagem;
        this.adquirido = adquirido;
        this.musica = musica;
        this.usuario = usuario;
    }

    public Musica getMusica() {
        return musica;
    }

    public void setMusica(Musica musica) {
        this.musica = musica;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
