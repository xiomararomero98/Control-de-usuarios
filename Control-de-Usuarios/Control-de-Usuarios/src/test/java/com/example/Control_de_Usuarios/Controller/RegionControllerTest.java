package com.example.Control_de_Usuarios.Controller;


import com.example.Control_de_Usuarios.Model.Region;
import com.example.Control_de_Usuarios.Service.RegionService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegionController.class)
public class RegionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegionService regionService;

    @Autowired
    private ObjectMapper objectMapper;

    private Region region;

    @BeforeEach
    void setUp() {
        region = new Region(1L, "Metropolitana", null);
    }

    @Test
    void obtenerRegiones_conContenido() throws Exception {
        when(regionService.obtenerRegiones()).thenReturn(List.of(region));

        mockMvc.perform(get("/api/v1/regiones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Metropolitana"));
    }

    @Test
    void obtenerRegiones_sinContenido() throws Exception {
        when(regionService.obtenerRegiones()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/regiones"))
                .andExpect(status().isNoContent());
    }

    @Test
    void obtenerRegionPorId_existente() throws Exception {
        when(regionService.obtenerRegionPorId(1L)).thenReturn(Optional.of(region));

        mockMvc.perform(get("/api/v1/regiones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Metropolitana"));
    }

    @Test
    void obtenerRegionPorId_noExiste() throws Exception {
        when(regionService.obtenerRegionPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/regiones/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void crearRegion() throws Exception {
        Region nueva = new Region(null, "Ñuble", null);
        Region creada = new Region(10L, "Ñuble", null);

        when(regionService.crearRegion(any(Region.class))).thenReturn(creada);

        mockMvc.perform(post("/api/v1/regiones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Ñuble"));
    }

    @Test
    void actualizarRegion_existente() throws Exception {
        Region actualizada = new Region(1L, "Actualizada", null);
        when(regionService.actualizarRegion(eq(1L), any(Region.class))).thenReturn(actualizada);

        mockMvc.perform(put("/api/v1/regiones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Actualizada"));
    }

    @Test
    void actualizarRegion_noExiste() throws Exception {
        Region regionInput = new Region(null, "Nueva", null);
        when(regionService.actualizarRegion(eq(100L), any(Region.class)))
                .thenThrow(new RuntimeException("Region no encontrada con ID:100"));

        mockMvc.perform(put("/api/v1/regiones/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(regionInput)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Region no encontrada con ID:100"));
    }

    @Test
    void eliminarRegion_existente() throws Exception {
        mockMvc.perform(delete("/api/v1/regiones/1"))
                .andExpect(status().isNoContent());

        verify(regionService, times(1)).eliminarRegionPorId(1L);
    }

    @Test
    void eliminarRegion_noExiste() throws Exception {
        doThrow(new RuntimeException("Region no encontrada con ID:200"))
                .when(regionService).eliminarRegionPorId(200L);

        mockMvc.perform(delete("/api/v1/regiones/200"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Region no encontrada con ID:200"));
    }
}