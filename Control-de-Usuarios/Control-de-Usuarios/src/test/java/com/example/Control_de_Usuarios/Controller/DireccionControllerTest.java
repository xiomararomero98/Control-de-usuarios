package com.example.Control_de_Usuarios.Controller;


import com.example.Control_de_Usuarios.Model.Comuna;
import com.example.Control_de_Usuarios.Model.Direccion;
import com.example.Control_de_Usuarios.Model.Usuario;
import com.example.Control_de_Usuarios.Service.DireccionService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DireccionController.class)
public class DireccionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DireccionService direccionService;

    private Direccion direccion;
    private Usuario usuario;
    private Comuna comuna;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);

        comuna = new Comuna();
        comuna.setId(1L);

        direccion = new Direccion();
        direccion.setId(1L);
        direccion.setCalle("Av. Las Palmas");
        direccion.setNumeracion(123);
        direccion.setNumeroDepartamento(2);
        direccion.setTorre("B");
        direccion.setUsuario(usuario);
        direccion.setComuna(comuna);
    }

    @Test
    void testObtenerDirecciones() throws Exception {
        when(direccionService.obtenerTodasLasDirecciones()).thenReturn(Arrays.asList(direccion));

        mockMvc.perform(get("/api/v1/direcciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].calle").value("Av. Las Palmas"));

        verify(direccionService, times(1)).obtenerTodasLasDirecciones();
    }

    @Test
    void testObtenerDireccionPorId_Existe() throws Exception {
        when(direccionService.obtenerDireccionPorId(1L)).thenReturn(Optional.of(direccion));

        mockMvc.perform(get("/api/v1/direcciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calle").value("Av. Las Palmas"));
    }

    @Test
    void testObtenerDireccionPorId_NoExiste() throws Exception {
        when(direccionService.obtenerDireccionPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/direcciones/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrearDireccion_OK() throws Exception {
        when(direccionService.crearDireccion(any(Direccion.class))).thenReturn(direccion);

        mockMvc.perform(post("/api/v1/direcciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(direccion)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.calle").value("Av. Las Palmas"));
    }

    @Test
    void testCrearDireccion_Fallo() throws Exception {
        when(direccionService.crearDireccion(any(Direccion.class))).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(post("/api/v1/direcciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(direccion)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testActualizarDireccion_OK() throws Exception {
        when(direccionService.actualizarDireccion(eq(1L), any(Direccion.class))).thenReturn(direccion);

        mockMvc.perform(put("/api/v1/direcciones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(direccion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calle").value("Av. Las Palmas"));
    }

    @Test
    void testActualizarDireccion_Error() throws Exception {
        when(direccionService.actualizarDireccion(eq(1L), any(Direccion.class)))
                .thenThrow(new RuntimeException("No encontrada"));

        mockMvc.perform(put("/api/v1/direcciones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(direccion)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarDireccion_OK() throws Exception {
        doNothing().when(direccionService).eliminarDireccionPorId(1L);

        mockMvc.perform(delete("/api/v1/direcciones/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarDireccion_Error() throws Exception {
        doThrow(new RuntimeException("No encontrada")).when(direccionService).eliminarDireccionPorId(1L);

        mockMvc.perform(delete("/api/v1/direcciones/1"))
                .andExpect(status().isNotFound());
    }
}