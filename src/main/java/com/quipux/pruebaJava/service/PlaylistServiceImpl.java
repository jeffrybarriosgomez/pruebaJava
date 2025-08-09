package com.quipux.pruebaJava.service;
import com.quipux.pruebaJava.entity.PlayList;
import com.quipux.pruebaJava.repository.PlaylistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository playlistRepository;
    @Override
    public PlayList create(PlayList playlist) {
        if (playlist.getNombre() == null || playlist.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la lista no puede ser vacío");
        }
        return playlistRepository.save(playlist);
    }

    @Override
    public List<PlayList> findAll() {
        return playlistRepository.findAll();
    }

    @Override
    public Optional<PlayList> findByNombre(String nombre) {
        return playlistRepository.findByNombre(nombre);
    }

    @Override
    @Transactional
    public void deleteByNombre(String nombre) {
        playlistRepository.deleteByNombre(nombre);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return playlistRepository.existsByNombre(nombre);
    }
}
