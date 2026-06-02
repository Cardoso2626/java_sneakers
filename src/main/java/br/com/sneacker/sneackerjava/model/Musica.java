package br.com.sneacker.sneackerjava.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_musica")
public class Musica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "musica_id")
    private Long id;
    @Column(name = "nome")
    private String nome;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "musica")
    private List<Sneaker> sneakers;

    public Musica() {
    }

    public Musica(Long id, String nome, List<Sneaker> sneakers) {
        this.id = id;
        this.nome = nome;
        this.sneakers = sneakers;
    }

    public List<Sneaker> getSneakers() {
        return sneakers;
    }

    public void setSneakers(List<Sneaker> sneakers) {
        this.sneakers = sneakers;
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
}
