package com.quipux.pruebaJava.controller;



import com.quipux.pruebaJava.entity.PlayList;
import com.quipux.pruebaJava.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/lists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    // Crear una nueva playlist
    @PostMapping
    public ResponseEntity<PlayList> create(@Valid @RequestBody PlayList playlist) {
        PlayList saved = playlistService.create(playlist);
        URI location = URI.create("/lists/" +
                URLEncoder.encode(saved.getNombre(), StandardCharsets.UTF_8));
        return ResponseEntity.created(location).body(saved);
    }

    // Listar todas las playlists
    @GetMapping
    public ResponseEntity<List<PlayList>> listAll() {
        return ResponseEntity.ok(playlistService.findAll());
    }

    // Obtener una playlist por nombre
    @GetMapping("/{listName}")
    public ResponseEntity<PlayList> get(@PathVariable String listName) {
        return playlistService.findByNombre(listName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar una playlist por nombre
    @DeleteMapping("/{listName}")
    public ResponseEntity<Void> delete(@PathVariable String listName) {
        if (!playlistService.existsByNombre(listName)) {
            return ResponseEntity.notFound().build();
        }
        playlistService.deleteByNombre(listName);
        return ResponseEntity.noContent().build();
    }
}