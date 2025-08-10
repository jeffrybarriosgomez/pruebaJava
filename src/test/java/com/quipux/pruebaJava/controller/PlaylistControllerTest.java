package com.quipux.pruebaJava.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quipux.pruebaJava.entity.PlayList;
import com.quipux.pruebaJava.entity.Song;
import com.quipux.pruebaJava.service.PlaylistServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;

@WebMvcTest(PlaylistController.class)
@AutoConfigureMockMvc(addFilters = false)
class PlayListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlaylistServiceImpl PlayListService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllPlayLists() throws Exception {
        given(PlayListService.findAll())
                .willReturn(List.of(new PlayList(1L, "Rock Hits", "Lista de rock", List.of())));

        mockMvc.perform(get("/lists"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Rock Hits"));
    }

    @Test
    void testGetPlayListByName() throws Exception {
        given(PlayListService.findByNombre("Rock Hits"))
                .willReturn(Optional.of(new PlayList(1L, "Rock Hits", "Lista de rock", List.of())));


        mockMvc.perform(get("/lists/Rock Hits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Rock Hits"));
    }

    @Test
    void testCreatePlayList() throws Exception {
        PlayList playList = new PlayList(
                1L,
                "Rock Hits",
                "Lista de rock",
                List.of(new Song(1L, "Song1", "Artist1", "Album1", "2024", "Rock"))
        );

        given(PlayListService.create(playList)).willReturn(playList);

        mockMvc.perform(post("/lists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playList)))
                .andExpect(status().isCreated()) // Cambiado de isOk() a isCreated()
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").value("Rock Hits"))
                .andExpect(jsonPath("$.descripcion").value("Lista de rock"))
                .andExpect(jsonPath("$.canciones[0].titulo").value("Song1"));
    }

    @Test
    void testDeletePlayList() throws Exception {
        String playlistName = "Rock Hits";
        when(PlayListService.existsByNombre(playlistName)).thenReturn(true);
        doNothing().when(PlayListService).deleteByNombre(playlistName);

        mockMvc.perform(delete("/lists/{nombre}", playlistName))
                .andExpect(status().isNoContent());
    }
}
