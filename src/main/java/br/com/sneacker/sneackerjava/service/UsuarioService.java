package br.com.sneacker.sneackerjava.service;


import br.com.sneacker.sneackerjava.dto.UsuarioRequest;
import br.com.sneacker.sneackerjava.dto.UsuarioResponse;
import br.com.sneacker.sneackerjava.model.Usuario;
import br.com.sneacker.sneackerjava.repository.UsuarioRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioService {
    public final UsuarioRepository usuarioRepository;
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public void deletarUsuario (Long id){
        usuarioRepository.deleteById(id);
    }

    public UsuarioResponse pegarPorId(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        return new UsuarioResponse(
                usuario.getEmail(),
                usuario.getNomeCompleto(),
                usuario.getCpf(),
                usuario.getSenha()
        );
    }

}
