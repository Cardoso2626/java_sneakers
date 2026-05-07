package br.com.sneacker.sneackerjava.dto;

import java.util.List;

public record MusicaRequest (String nome, List<Long> idSneakers){
}
