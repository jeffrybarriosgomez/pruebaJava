import { Component, OnInit } from '@angular/core';
import { PlaylistService, Playlist } from '../../services/playlist';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-playlist-list',
  imports: [CommonModule, RouterModule],
  templateUrl: './playlist-list.html',
  styleUrl: './playlist-list.css'
})
export class PlaylistList implements OnInit {
  playlists: Playlist[] = [];

  constructor(private playlistService: PlaylistService) {}

  ngOnInit(): void {
    this.loadPlaylists();
  }

  loadPlaylists() {
  this.playlistService.getPlaylists().subscribe({
    next: (data) => {
      this.playlists = data;
      console.log('Datos recibidos:', this.playlists);
    },
    error: (err) => console.error('Error al cargar playlists', err)
  });
}

  delete(nombre: string) {
    if (confirm(`¿Eliminar playlist "${nombre}"?`)) {
      this.playlistService.deletePlaylist(nombre).subscribe(() => this.loadPlaylists());
    }
  }
}
