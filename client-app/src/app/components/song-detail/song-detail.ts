import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Song } from '../../services/playlist';

@Component({
  selector: 'app-song-detail',
  imports: [CommonModule, FormsModule],
  templateUrl: './song-detail.html',
  styleUrl: './song-detail.css'
})
export class SongDetail {
  @Input() song!: Song;
  @Input() index!: number;
  @Output() remove = new EventEmitter<number>();

  removeSong() {
    this.remove.emit(this.index);
  }
}
