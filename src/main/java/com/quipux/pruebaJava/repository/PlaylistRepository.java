package com.quipux.pruebaJava.repository;

import com.quipux.pruebaJava.entity.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<PlayList, Long> {
    Optional<PlayList> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
    void deleteByNombre(String nombre);
}