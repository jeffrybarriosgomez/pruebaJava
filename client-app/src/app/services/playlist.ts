import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Song {
  titulo: string;
  artista: string;
  album: string;
  anno: string;
  genero: string;
}

export interface Playlist {
  id?: number;
  nombre: string;
  descripcion?: string;
  canciones: Song[];
}

@Injectable({
  providedIn: 'root'
})
export class PlaylistService {

  private apiUrl = '/lists';

  constructor(private http: HttpClient) {}

  private authHeaders(): HttpHeaders {
    const username = 'admin';
    const password = 'admin';
    const basicAuth = 'Basic ' + btoa(username + ':' + password);
    return new HttpHeaders({ 'Authorization': basicAuth });
  }

  getPlaylists(): Observable<Playlist[]> {
    return this.http.get<Playlist[]>(this.apiUrl, { headers: this.authHeaders() });
  }

  getPlaylist(nombre: string): Observable<Playlist> {
    return this.http.get<Playlist>(`${this.apiUrl}/${encodeURIComponent(nombre)}`, { headers: this.authHeaders() });
  }

  createPlaylist(playlist: Playlist): Observable<Playlist> {
    return this.http.post<Playlist>(this.apiUrl, playlist, { headers: this.authHeaders() });
  }

  deletePlaylist(nombre: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${encodeURIComponent(nombre)}`, { headers: this.authHeaders() });
  }
}
