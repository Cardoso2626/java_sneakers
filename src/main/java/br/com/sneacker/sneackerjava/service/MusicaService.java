package br.com.sneacker.sneackerjava.service;

import br.com.sneacker.sneackerjava.dto.MusicaRequest;
import br.com.sneacker.sneackerjava.dto.MusicaResponse;
import br.com.sneacker.sneackerjava.model.Musica;
import br.com.sneacker.sneackerjava.model.Sneaker;
import br.com.sneacker.sneackerjava.repository.MusicaRepository;
import br.com.sneacker.sneackerjava.repository.SneakerRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicaService {
    public final MusicaRepository musicaRepository;
    public final SneakerRepository sneakerRepository;

    public MusicaService(MusicaRepository musicaRepository, SneakerRepository sneakerRepository) {
        this.musicaRepository = musicaRepository;
        this.sneakerRepository = sneakerRepository;
    }

    public MusicaResponse criarMusica(MusicaRequest musicaRequest) {
        Musica musica = new Musica();
        musica.setNome(musicaRequest.nome());
        List<Long> idSneakers = new ArrayList<>();

        if (musicaRequest.idSneakers() != null) {
            List<Sneaker> sneakers = sneakerRepository.findAllById(musicaRequest.idSneakers());
            idSneakers = sneakers.stream().map(Sneaker::getId).collect(Collectors.toList());
            musica.setSneakers(sneakers);
        } else {
            musica.setSneakers(null);
        }

        musica = musicaRepository.save(musica);
        return new MusicaResponse(
                musica.getNome(),
                idSneakers
        );
    }

    @Transactional
    @Cacheable(value = "musica", key = "#id")
    public MusicaResponse pegarPorId(Long id) {
        Musica musica = musicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Musica não encontrada"));

        List<Long> sneakersIds = musica.getSneakers() != null
                ? musica.getSneakers().stream().map(Sneaker::getId).collect(Collectors.toList())
                : new ArrayList<>();

        return new MusicaResponse(
                musica.getNome(),
                sneakersIds
        );
    }

    @Transactional
    @CacheEvict(value = "musica", key = "#id")
    public void deletarMusica(Long id) {
        Musica musica = musicaRepository.findById(id).orElseThrow(() -> new RuntimeException("Musica não encontrada"));
        musicaRepository.deleteById(id);
    }
}
