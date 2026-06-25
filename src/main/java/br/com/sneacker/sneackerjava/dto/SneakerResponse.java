package br.com.sneacker.sneackerjava.dto;

public record SneakerResponse(Long id, String nome, String marca, Double preco, String imagem, Boolean adquirido, String nomeMusica, String emailUsuario ) {
}
