package br.com.sneacker.sneackerjava.service;

import br.com.sneacker.sneackerjava.dto.UsuarioResponse;
import br.com.sneacker.sneackerjava.model.Usuario;
import br.com.sneacker.sneackerjava.repository.UsuarioRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioService {
    public final UsuarioRepository usuarioRepository;
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @Transactional
    @CacheEvict(value = "usuario", key = "#id")
    public void deletarUsuario (Long id){
        usuarioRepository.deleteById(id);
    }

    @Transactional
    @Cacheable(value = "usuario", key = "#id")
    public UsuarioResponse pegarPorId(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        return new UsuarioResponse(
                usuario.getEmail(),
                usuario.getNomeCompleto()
        );
    }

}
