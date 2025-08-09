package com.quipux.pruebaJava.service;

import com.quipux.pruebaJava.entity.PlayList;

import java.util.List;
import java.util.Optional;

public interface PlaylistService {
    PlayList create(PlayList playlist);
    List<PlayList> findAll();
    Optional<PlayList> findByNombre(String nombre);
    void deleteByNombre(String nombre);
    boolean existsByNombre(String nombre);
}