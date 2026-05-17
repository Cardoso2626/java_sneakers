package br.com.sneacker.sneackerjava.controller;

import br.com.sneacker.sneackerjava.dto.MusicaRequest;
import br.com.sneacker.sneackerjava.dto.MusicaResponse;
import br.com.sneacker.sneackerjava.service.MusicaService;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/musica")
public class MusicaController {
    public final MusicaService musicaService;
    public MusicaController(MusicaService musicaService) {
        this.musicaService = musicaService;
    }


    @PostMapping("/criar")
    public ResponseEntity<MusicaResponse> criarMusica(@RequestBody MusicaRequest musicaRequest){
        MusicaResponse musica =  musicaService.criarMusica(musicaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(musica);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicaResponse> pegarMusica(@PathVariable Long id){
        MusicaResponse musica = musicaService.pegarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(musica);
    }

    @DeleteMapping("/{id}")
    public void deletarMusica(@PathVariable Long id){
        musicaService.deletarMusica(id);
    }


}
