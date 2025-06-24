package com.example.Control_de_Usuarios.Controller;


import com.example.Control_de_Usuarios.Model.Rol;
import com.example.Control_de_Usuarios.Service.RolService;
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
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RolController.class)
public class RolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RolService rolService;

    @Autowired
    private ObjectMapper objectMapper;

    private Rol rol1;
    private Rol rol2;

    @BeforeEach
    void setUp() {
        rol1 = new Rol(1L, "ADMIN");
        rol2 = new Rol(2L, "CLIENTE");
    }

    @Test
    void testObtenerRoles_conResultados() throws Exception {
        when(rolService.obtenerRoles()).thenReturn(Arrays.asList(rol1, rol2));

        mockMvc.perform(get("/api/v1/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testObtenerRoles_sinResultados() throws Exception {
        when(rolService.obtenerRoles()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/roles"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testObtenerRolPorId_existente() throws Exception {
        when(rolService.obtenerRolPorId(1L)).thenReturn(rol1);

        mockMvc.perform(get("/api/v1/roles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("ADMIN"));
    }

    @Test
    void testObtenerRolPorId_noExistente() throws Exception {
        when(rolService.obtenerRolPorId(1L)).thenThrow(new RuntimeException("Rol no encontrado"));

        mockMvc.perform(get("/api/v1/roles/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrearRol() throws Exception {
        when(rolService.crearRol(any(Rol.class))).thenReturn(rol1);

        mockMvc.perform(post("/api/v1/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rol1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("ADMIN"));
    }

    @Test
    void testActualizarRol_existente() throws Exception {
        Rol actualizado = new Rol(1L, "GERENTE");
        when(rolService.actualizarRol(eq(1L), any(Rol.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/v1/roles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("GERENTE"));
    }

    @Test
    void testActualizarRol_noExistente() throws Exception {
        Rol actualizado = new Rol(1L, "GERENTE");
        when(rolService.actualizarRol(eq(1L), any(Rol.class))).thenThrow(new RuntimeException("Rol no encontrado"));

        mockMvc.perform(put("/api/v1/roles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarRol() throws Exception {
        doNothing().when(rolService).eliminarRol(1L);

        mockMvc.perform(delete("/api/v1/roles/1"))
                .andExpect(status().isNoContent());
    }
}