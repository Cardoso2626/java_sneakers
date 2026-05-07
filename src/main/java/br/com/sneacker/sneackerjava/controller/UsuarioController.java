package br.com.sneacker.sneackerjava.controller;



import br.com.sneacker.sneackerjava.security.dto.LoginRequestDTO;
import br.com.sneacker.sneackerjava.security.dto.LoginResponseDTO;
import br.com.sneacker.sneackerjava.dto.UsuarioRequest;
import br.com.sneacker.sneackerjava.dto.UsuarioResponse;
import br.com.sneacker.sneackerjava.model.Usuario;
import br.com.sneacker.sneackerjava.repository.UsuarioRepository;
import br.com.sneacker.sneackerjava.security.service.TokenService;
import br.com.sneacker.sneackerjava.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    public UsuarioService usuarioService;
    @Autowired
    public AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;


    @PostMapping("/auth/login")
    public ResponseEntity login (@RequestBody @Valid LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }


    @PostMapping("/auth/register")
    public ResponseEntity register (@RequestBody @Valid UsuarioRequest data) {
        if(this.repository.findByEmail(data.getEmail()) != null) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getSenha());
        Usuario user = new Usuario(data.getEmail(),data.getNomeCompleto(),data.getCpf(), encryptedPassword, data.getSneakers(),data.getRole());

        this.repository.save(user);

        return ResponseEntity.ok().build();
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
