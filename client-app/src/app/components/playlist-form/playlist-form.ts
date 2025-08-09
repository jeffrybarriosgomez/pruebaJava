import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { PlaylistService, Playlist, Song } from '../../services/playlist';
import { SongDetail } from '../song-detail/song-detail';
@Component({
  selector: 'app-playlist-form',
  imports: [CommonModule, FormsModule, SongDetail],
  templateUrl: './playlist-form.html',
  styleUrl: './playlist-form.css'
})
export class PlaylistForm {
  nombre = '';
  descripcion = '';
  canciones: Song[] = [];

  constructor(private playlistService: PlaylistService, private router: Router) {}

  addSong() {
    this.canciones.push({ titulo: '', artista: '', album: '', anno: '', genero: '' });
  }

  removeSong(index: number) {
    this.canciones.splice(index, 1);
  }

  submit() {
    if (!this.nombre) {
      alert('El nombre es obligatorio');
      return;
    }
    const nueva: Playlist = {
      nombre: this.nombre,
      descripcion: this.descripcion,
      canciones: this.canciones
    };
    this.playlistService.createPlaylist(nueva).subscribe(() => {
      alert('Playlist creada');
      this.router.navigate(['/']);
    });
  }
}
