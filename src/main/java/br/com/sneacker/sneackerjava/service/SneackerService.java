package br.com.sneacker.sneackerjava.service;

import br.com.sneacker.sneackerjava.dto.SneackerRequest;
import br.com.sneacker.sneackerjava.dto.SneackerResponse;
import br.com.sneacker.sneackerjava.model.Musica;
import br.com.sneacker.sneackerjava.model.Sneacker;
import br.com.sneacker.sneackerjava.model.Usuario;
import br.com.sneacker.sneackerjava.repository.MusicaRepository;
import br.com.sneacker.sneackerjava.repository.SneackerRepository;
import br.com.sneacker.sneackerjava.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SneackerService {
    public final SneackerRepository sneackerRepository;
    public final UsuarioRepository usuarioRepository;
    public final MusicaRepository musicaRepository;
    public SneackerService(SneackerRepository sneackerRepository, UsuarioRepository usuarioRepository, MusicaRepository musicaRepository) {
        this.sneackerRepository = sneackerRepository;
        this.usuarioRepository = usuarioRepository;
        this.musicaRepository = musicaRepository;
    }
    
    public List<SneackerResponse> listarSneackers(){
        List<Sneacker> sneackers = sneackerRepository.findAll();
        return sneackers.stream()
                .map(s -> new SneackerResponse(
                        s.getId(),
                        s.getNome(),
                        s.getMarca(),
                        s.getPreco(),
                        s.getAdquirido(),
                        s.getMusica() != null ? s.getMusica().getNome() : null,
                        s.getUsuario() != null ? s.getUsuario().getEmail() : null
                )).collect(Collectors.toList());
    }

    public SneackerResponse criarSneacker(SneackerRequest sneackerRequest) {
        Sneacker sneacker = new Sneacker();
        sneacker.setNome(sneackerRequest.getNome());
        sneacker.setMarca(sneackerRequest.getMarca());
        sneacker.setPreco(sneackerRequest.getPreco());
        sneacker.setAdquirido(sneackerRequest.getAdquirido());
        if (sneackerRequest.getNomeMusica() != null){
            Musica musica = musicaRepository.findByNome(sneackerRequest.getNomeMusica()).orElseThrow(() -> new RuntimeException ("Não foi possível encontrar a música: " + sneackerRequest.getNomeMusica()));
            sneacker.setMusica(musica);
        } else{
            sneacker.setMusica(null);
        }
        if (sneackerRequest.getEmailUsuario() != null) {
            Usuario usuario = usuarioRepository.findByEmail(sneackerRequest.getEmailUsuario()).orElseThrow(() -> new RuntimeException("O usuário não pode ser encontrado: " + sneackerRequest.getEmailUsuario()));
            sneacker.setUsuario(usuario);
        }else{
            sneacker.setUsuario(null);
        }

        sneacker = sneackerRepository.save(sneacker);

        return new SneackerResponse(
                sneacker.getId(),
                sneacker.getNome(),
                sneacker.getMarca(),
                sneacker.getPreco(),
                sneacker.getAdquirido(),
                sneacker.getMusica() != null ? sneacker.getMusica().getNome() : null,
                sneacker.getUsuario() != null ? sneacker.getUsuario().getEmail() : null
        );
    }

    public void deletarSneacker(Long id){
        Sneacker sneacker = sneackerRepository.findById(id).orElseThrow(() -> new RuntimeException("Sneacker não identificado!"));
        sneackerRepository.delete(sneacker);
    }

    public SneackerResponse atualizarSneacker (SneackerRequest sneackerRequest, Long id) {
        Sneacker sneacker = sneackerRepository.findById(id).orElseThrow(() -> new RuntimeException("Sneacker não encontrado"));
        sneacker.setNome(sneackerRequest.getNome());
        sneacker.setMarca(sneackerRequest.getMarca());
        sneacker.setPreco(sneackerRequest.getPreco());
        sneacker.setAdquirido(sneackerRequest.getAdquirido());
        if (sneackerRequest.getNomeMusica() != null){
            Musica musica = musicaRepository.findByNome(sneackerRequest.getNomeMusica()).orElseThrow(() -> new RuntimeException ("Não foi possível encontrar a música: " + sneackerRequest.getNomeMusica()));
            sneacker.setMusica(musica);
        }
        if (sneackerRequest.getEmailUsuario() != null) {
            Usuario usuario = usuarioRepository.findByEmail(sneackerRequest.getEmailUsuario()).orElseThrow(() -> new RuntimeException("O usuário não pode ser encontrado: " + sneackerRequest.getEmailUsuario()));
            sneacker.setUsuario(usuario);
        }

        sneacker = sneackerRepository.save(sneacker);
        return new SneackerResponse(
                sneacker.getId(),
                sneacker.getNome(),
                sneacker.getMarca(),
                sneacker.getPreco(),
                sneacker.getAdquirido(),
                sneacker.getMusica() != null ? sneacker.getMusica().getNome() : null,
                sneacker.getUsuario() != null ? sneacker.getUsuario().getEmail() : null
        );
    }
}
