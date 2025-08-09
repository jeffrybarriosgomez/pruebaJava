package com.quipux.pruebaJava.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "playlists",
    uniqueConstraints = @UniqueConstraint(columnNames = "nombre")
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "playlist_id")
    @Builder.Default
    private List<Song> canciones = new ArrayList<>();
}
