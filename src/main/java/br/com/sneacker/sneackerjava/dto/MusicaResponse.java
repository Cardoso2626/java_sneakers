package br.com.sneacker.sneackerjava.dto;

import java.util.List;

public record MusicaResponse(String nome, List<Long> idSneakers) {
}
