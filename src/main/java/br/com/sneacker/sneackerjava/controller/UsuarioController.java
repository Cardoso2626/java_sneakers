package br.com.sneacker.sneackerjava.controller;



import br.com.sneacker.sneackerjava.dto.UsuarioRequest;
import br.com.sneacker.sneackerjava.dto.UsuarioResponse;
import br.com.sneacker.sneackerjava.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    public final UsuarioService usuarioService;
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @PostMapping("/criar")
    public ResponseEntity<UsuarioResponse> criarUsuario(@RequestBody @Valid UsuarioRequest usuarioRequest) {
        UsuarioResponse usuarioResponse = usuarioService.criarUsuario(usuarioRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> pegarUsuario(@PathVariable Long id){
        UsuarioResponse usuarioResponse = usuarioService.pegarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioResponse);
    }
    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id){
        usuarioService.deletarUsuario(id);
    }

}
