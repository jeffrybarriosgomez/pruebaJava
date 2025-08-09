import { Routes } from '@angular/router';
import { PlaylistList } from '../app/components/playlist-list/playlist-list';
import { PlaylistForm } from '../app/components/playlist-form/playlist-form';

export const routes: Routes = [
    { path: '', component: PlaylistList },
    { path: 'create', component: PlaylistForm },
];

