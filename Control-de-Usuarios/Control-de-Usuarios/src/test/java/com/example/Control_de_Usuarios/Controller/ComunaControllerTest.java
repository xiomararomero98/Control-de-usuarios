package com.example.Control_de_Usuarios.Controller;

import com.example.Control_de_Usuarios.Model.Comuna;
import com.example.Control_de_Usuarios.Model.Region;
import com.example.Control_de_Usuarios.Service.ComunaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ComunaController.class)
public class ComunaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComunaService comunaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testObtenerComunas_conResultados() throws Exception {
        Comuna comuna = new Comuna(1L, "Santiago", new Region(), null);
        Mockito.when(comunaService.obtenerTodasLasComunas()).thenReturn(Arrays.asList(comuna));

        mockMvc.perform(get("/api/v1/comunas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Santiago"));
    }

    @Test
    void testObtenerComunas_sinResultados() throws Exception {
        Mockito.when(comunaService.obtenerTodasLasComunas()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/v1/comunas"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testObtenerComunaPorId_existente() throws Exception {
        Comuna comuna = new Comuna(1L, "Santiago", new Region(), null);
        Mockito.when(comunaService.obtenerComunaPorId(1L)).thenReturn(Optional.of(comuna));

        mockMvc.perform(get("/api/v1/comunas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Santiago"));
    }

    @Test
    void testObtenerComunaPorId_noExistente() throws Exception {
        Mockito.when(comunaService.obtenerComunaPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/comunas/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrearComuna_exito() throws Exception {
    // Simulamos la comuna a crear
    Region region = new Region(1L, "Metropolitana", null);
    Comuna comuna = new Comuna(null, "Ñuñoa", region, null);
    Comuna comunaGuardada = new Comuna(1L, "Ñuñoa", region, null);

    // Configuramos el mock para que devuelva la comuna guardada
    when(comunaService.crearComuna(eq("Ñuñoa"), eq(1L))).thenReturn(comunaGuardada);

    // JSON simula correctamente el body anidado
    String bodyJson = """
    {
      "nombre": "Ñuñoa",
      "region": {
        "id": 1
      }
    }
    """;

    mockMvc.perform(post("/api/v1/comunas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(bodyJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nombre").value("Ñuñoa"));
}
    @Test
    void testCrearComuna_errorRegion() throws Exception {
    Comuna comuna = new Comuna(null, "Comuna Fantasma", null, null);

    mockMvc.perform(post("/api/v1/comunas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(comuna)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(org.hamcrest.Matchers.containsString("Cannot invoke")));
}
    @Test
    void testActualizarComuna_exito() throws Exception {
        Region region = new Region(1L, "Metropolitana", null);
        Comuna comuna = new Comuna(null, "Ñuñoa", region, null);
        Comuna actualizada = new Comuna(1L, "Ñuñoa", region, null);

        Mockito.when(comunaService.actualizarComuna(eq(1L), any(Comuna.class))).thenReturn(actualizada);

        mockMvc.perform(put("/api/v1/comunas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comuna)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Ñuñoa"));
    }

    @Test
    void testActualizarComuna_error() throws Exception {
        Region region = new Region(1L, "Metropolitana", null);
        Comuna comuna = new Comuna(null, "Ñuñoa", region, null);

        Mockito.when(comunaService.actualizarComuna(eq(1L), any(Comuna.class)))
                .thenThrow(new RuntimeException("Comuna no encontrada con ID: 1"));

        mockMvc.perform(put("/api/v1/comunas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comuna)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Comuna no encontrada con ID: 1"));
    }

    @Test
    void testEliminarComuna_exito() throws Exception {
        mockMvc.perform(delete("/api/v1/comunas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarComuna_error() throws Exception {
        Mockito.doThrow(new RuntimeException("Error al eliminar comuna")).when(comunaService).eliminarComunaPorId(1L);

        mockMvc.perform(delete("/api/v1/comunas/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Error al eliminar comuna"));
    }
}