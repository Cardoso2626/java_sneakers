package br.com.sneacker.sneackerjava.service;

import br.com.sneacker.sneackerjava.dto.SneakerRequest;
import br.com.sneacker.sneackerjava.dto.SneakerResponse;
import br.com.sneacker.sneackerjava.model.Musica;
import br.com.sneacker.sneackerjava.model.Sneaker;
import br.com.sneacker.sneackerjava.model.Usuario;
import br.com.sneacker.sneackerjava.repository.MusicaRepository;
import br.com.sneacker.sneackerjava.repository.SneakerRepository;
import br.com.sneacker.sneackerjava.repository.UsuarioRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SneakerService {
    public final SneakerRepository sneakerRepository;
    public final UsuarioRepository usuarioRepository;
    public final MusicaRepository musicaRepository;

    public SneakerService(SneakerRepository sneakerRepository, UsuarioRepository usuarioRepository, MusicaRepository musicaRepository) {
        this.sneakerRepository = sneakerRepository;
        this.usuarioRepository = usuarioRepository;
        this.musicaRepository = musicaRepository;
    }

    @Transactional
    @Cacheable(value = "listaTenis")
    public List<SneakerResponse> listarSneakers(){
        List<Sneaker> sneakers = sneakerRepository.findAll();
        return sneakers.stream()
                .map(s -> new SneakerResponse(
                        s.getId(),
                        s.getNome(),
                        s.getMarca(),
                        s.getPreco(),
                        s.getImagem(),
                        s.getAdquirido(),
                        s.getMusica() != null ? s.getMusica().getNome() : null,
                        s.getUsuario() != null ? s.getUsuario().getEmail() : null
                )).collect(Collectors.toList());
    }

    @Transactional
    @Cacheable(value = "tenis", key = "#id")
    public SneakerResponse pegarPorId(Long id){
        Sneaker sneaker = sneakerRepository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado"));
        return new SneakerResponse(
                sneaker.getId(),
                sneaker.getNome(),
                sneaker.getMarca(),
                sneaker.getPreco(),
                sneaker.getImagem(),
                sneaker.getAdquirido(),
                sneaker.getMusica() != null ? sneaker.getMusica().getNome() : null,
                sneaker.getUsuario() != null ? sneaker.getUsuario().getEmail() : null
        );
    }

    @Transactional
    @CacheEvict(
            value = {"listaTenis", "tenisPorUsuario", "musica"},
            allEntries = true
    )
    public SneakerResponse criarSneaker(SneakerRequest sneakerRequest) {
        Sneaker sneaker = new Sneaker();
        sneaker.setNome(sneakerRequest.getNome());
        sneaker.setMarca(sneakerRequest.getMarca());
        sneaker.setPreco(sneakerRequest.getPreco());
        sneaker.setImagem(sneakerRequest.getImagem());
        sneaker.setAdquirido(sneakerRequest.getAdquirido());

        if (sneakerRequest.getNomeMusica() != null){
            Musica musica = musicaRepository.findByNome(sneakerRequest.getNomeMusica())
                    .orElseThrow(() -> new RuntimeException("Não foi possível encontrar a música: " + sneakerRequest.getNomeMusica()));
            sneaker.setMusica(musica);
        } else {
            sneaker.setMusica(null);
        }

        if (sneakerRequest.getEmailUsuario() != null) {
            Usuario usuario = usuarioRepository.findOptionalByEmail(sneakerRequest.getEmailUsuario())
                    .orElseThrow(() -> new RuntimeException("O usuário não pode ser encontrado: " + sneakerRequest.getEmailUsuario()));
            sneaker.setUsuario(usuario);
        } else {
            sneaker.setUsuario(null);
        }

        sneaker = sneakerRepository.save(sneaker);

        return new SneakerResponse(
                sneaker.getId(),
                sneaker.getNome(),
                sneaker.getMarca(),
                sneaker.getPreco(),
                sneaker.getImagem(),
                sneaker.getAdquirido(),
                sneaker.getMusica() != null ? sneaker.getMusica().getNome() : null,
                sneaker.getUsuario() != null ? sneaker.getUsuario().getEmail() : null
        );
    }


    @Transactional
    @CacheEvict(
            value = {"listaTenis", "tenisPorUsuario", "musica", "tenis"},
            allEntries = true
    )
    public void deletarSneaker(Long id){
        Sneaker sneaker = sneakerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sneaker não identificado!"));
        sneakerRepository.delete(sneaker);
    }



    @Transactional
    @CacheEvict(
            value = {"listaTenis", "tenisPorUsuario", "musica", "tenis"},
            allEntries = true
    )
    public SneakerResponse atualizarSneaker(SneakerRequest sneakerRequest, Long id) {
        Sneaker sneaker = sneakerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sneaker não encontrado"));
        sneaker.setNome(sneakerRequest.getNome());
        sneaker.setMarca(sneakerRequest.getMarca());
        sneaker.setPreco(sneakerRequest.getPreco());
        sneaker.setImagem(sneakerRequest.getImagem());
        sneaker.setAdquirido(sneakerRequest.getAdquirido());

        if (sneakerRequest.getNomeMusica() != null){
            Musica musica = musicaRepository.findByNome(sneakerRequest.getNomeMusica())
                    .orElseThrow(() -> new RuntimeException("Não foi possível encontrar a música: " + sneakerRequest.getNomeMusica()));
            sneaker.setMusica(musica);
        }

        if (sneakerRequest.getEmailUsuario() != null) {
            Usuario usuario = usuarioRepository.findOptionalByEmail(sneakerRequest.getEmailUsuario())
                    .orElseThrow(() -> new RuntimeException("O usuário não pode ser encontrado: " + sneakerRequest.getEmailUsuario()));
            sneaker.setUsuario(usuario);
        }

        sneaker = sneakerRepository.save(sneaker);

        return new SneakerResponse(
                sneaker.getId(),
                sneaker.getNome(),
                sneaker.getMarca(),
                sneaker.getPreco(),
                sneaker.getImagem(),
                sneaker.getAdquirido(),
                sneaker.getMusica() != null ? sneaker.getMusica().getNome() : null,
                sneaker.getUsuario() != null ? sneaker.getUsuario().getEmail() : null
        );
    }

    @Transactional
    @Cacheable(value = "tenisPorUsuario", key="#emailUsuario")
    public List<SneakerResponse> listarTenisPorEmailUsuario(String emailUsuario) {
        Usuario usuario = usuarioRepository.findOptionalByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Email de usuário não encontrado: " + emailUsuario));
        List<Sneaker> sneakers = usuario.getSneakers();
        return sneakers.stream()
                .map(s -> new SneakerResponse(
                        s.getId(),
                        s.getNome(),
                        s.getMarca(),
                        s.getPreco(),
                        s.getImagem(),
                        s.getAdquirido(),
                        s.getMusica() != null ? s.getMusica().getNome() : null,
                        s.getUsuario() != null ? s.getUsuario().getEmail() : null
                )).collect(Collectors.toList());
    }
}
