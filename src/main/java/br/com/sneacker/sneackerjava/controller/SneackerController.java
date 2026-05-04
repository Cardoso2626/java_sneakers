package br.com.sneacker.sneackerjava.controller;

import br.com.sneacker.sneackerjava.dto.SneackerRequest;
import br.com.sneacker.sneackerjava.dto.SneackerResponse;
import br.com.sneacker.sneackerjava.service.SneackerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenis")
public class SneackerController {
    public final SneackerService sneackerService;
    public SneackerController(SneackerService sneackerService) {
        this.sneackerService = sneackerService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<SneackerResponse>> listarSneackers(){
        List<SneackerResponse> sneackers = sneackerService.listarSneackers();
        return ResponseEntity.status(HttpStatus.OK).body(sneackers);
    }


    @PostMapping("/criar")
    public ResponseEntity<SneackerResponse> criarSneacker(@RequestBody @Valid SneackerRequest sneackerRequest) {
        SneackerResponse sneacker = sneackerService.criarSneacker(sneackerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(sneacker);
    }

    @DeleteMapping("/{id}")
    public void deletarSneacker(@PathVariable Long id){
        sneackerService.deletarSneacker(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SneackerResponse> atualizarSneacker(@PathVariable Long id, @RequestBody @Valid SneackerRequest sneackerRequest){
        SneackerResponse sneacker = sneackerService.atualizarSneacker(sneackerRequest, id);
        return ResponseEntity.ok(sneacker);
    }

    @GetMapping("/listar/{email}")
    public ResponseEntity<List<SneackerResponse>> listarSneackerPorEmail(@PathVariable String email){
        List<SneackerResponse> sneackers = sneackerService.listarTenisPorEmailUsuario(email);
        return ResponseEntity.status(HttpStatus.OK).body(sneackers);
    }
}
