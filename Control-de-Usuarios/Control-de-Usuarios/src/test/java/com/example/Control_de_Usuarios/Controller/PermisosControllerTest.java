package com.example.Control_de_Usuarios.Controller;
import com.example.Control_de_Usuarios.Model.Permisos;
import com.example.Control_de_Usuarios.Model.Privilegios;
import com.example.Control_de_Usuarios.Model.Rol;
import com.example.Control_de_Usuarios.Service.PermisosService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PermisosController.class)
class PermisosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PermisosService permisosService;

    private Permisos permiso;
    private Privilegios privilegio;
    private Rol rol;

    @BeforeEach
    void setUp() {
        privilegio = new Privilegios();
        privilegio.setId(1L);
        privilegio.setNombre("Ver stock");

        rol = new Rol();
        rol.setId(1L);
        rol.setNombre("Administrador");

        permiso = new Permisos();
        permiso.setId(1L);
        permiso.setPrivilegio(privilegio);
        permiso.setRol(rol);
    }

    @Test
    void testObtenerPermisos_conResultados() throws Exception {
        when(permisosService.obtenerPermisos()).thenReturn(Arrays.asList(permiso));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/permisos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void testObtenerPermisos_sinResultados() throws Exception {
        when(permisosService.obtenerPermisos()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/permisos"))
            .andExpect(status().isNoContent());
    }

    @Test
    void testObtenerPermisoPorId_existente() throws Exception {
        when(permisosService.obtenerPermisoPorId(1L)).thenReturn(permiso);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/permisos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testObtenerPermisoPorId_noExistente() throws Exception {
        when(permisosService.obtenerPermisoPorId(99L)).thenThrow(new RuntimeException("No encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/permisos/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void testCrearPermiso() throws Exception {
        when(permisosService.crearPermisos(any(Permisos.class))).thenReturn(permiso);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(permiso);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/permisos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
void testEliminarPermiso_error() throws Exception {
    // Simula que el permiso no existe y se lanza una excepción al intentar eliminar
    doThrow(new RuntimeException("No encontrado")).when(permisosService).eliminarPermisoPorId(1L);

    mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/permisos/1"))
            .andExpect(status().isInternalServerError())
            .andExpect(content().string("No encontrado"));
}
}