package br.com.sneacker.sneackerjava.controller;

import br.com.sneacker.sneackerjava.dto.SneakerRequest;
import br.com.sneacker.sneackerjava.dto.SneakerResponse;
import br.com.sneacker.sneackerjava.service.SneakerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenis")
public class SneakerController {
    public final SneakerService sneakerService;

    public SneakerController(SneakerService sneakerService) {
        this.sneakerService = sneakerService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<SneakerResponse>> listarSneakers(){
        List<SneakerResponse> sneakers = sneakerService.listarSneakers();
        return ResponseEntity.status(HttpStatus.OK).body(sneakers);
    }

    @PostMapping("/criar")
    public ResponseEntity<SneakerResponse> criarSneaker(@RequestBody @Valid SneakerRequest sneakerRequest) {
        SneakerResponse sneaker = sneakerService.criarSneaker(sneakerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(sneaker);
    }

    @DeleteMapping("/{id}")
    public void deletarSneaker(@PathVariable Long id){
        sneakerService.deletarSneaker(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SneakerResponse> atualizarSneaker(@PathVariable Long id, @RequestBody @Valid SneakerRequest sneakerRequest){
        SneakerResponse sneaker = sneakerService.atualizarSneaker(sneakerRequest, id);
        return ResponseEntity.ok(sneaker);
    }

    @GetMapping("/listar/{email}")
    public ResponseEntity<List<SneakerResponse>> listarSneakerPorEmail(@PathVariable String email){
        List<SneakerResponse> sneakers = sneakerService.listarTenisPorEmailUsuario(email);
        return ResponseEntity.status(HttpStatus.OK).body(sneakers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SneakerResponse> pegarSneakerPorId(@PathVariable Long id){
        SneakerResponse sneakerResponse = sneakerService.pegarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(sneakerResponse);
    }

}
