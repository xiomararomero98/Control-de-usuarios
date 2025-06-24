package com.example.Control_de_Usuarios.Controller;

import com.example.Control_de_Usuarios.Model.Privilegios;
import com.example.Control_de_Usuarios.Service.PrivilegiosService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PrivilegiosController.class)
public class PrivilegiosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrivilegiosService privilegiosService;

    private ObjectMapper mapper = new ObjectMapper();

    private Privilegios p1;

    @BeforeEach
    void setUp() {
        p1 = new Privilegios(1L, "Gestionar comentarios", null);
    }

    @Test
    public void testObtenerPrivilegios_conResultados() throws Exception {
        List<Privilegios> lista = List.of(p1);
        when(privilegiosService.obtenerPrivilegios()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/privilegios"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nombre").value("Gestionar comentarios"));
    }

    @Test
    public void testObtenerPrivilegios_sinResultados() throws Exception {
        when(privilegiosService.obtenerPrivilegios()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/privilegios"))
            .andExpect(status().isNoContent());
    }

    @Test
    public void testObtenerPrivilegioPorId_existente() throws Exception {
        when(privilegiosService.obtenerPrivilegioPorId(1L)).thenReturn(Optional.of(p1));

        mockMvc.perform(get("/api/v1/privilegios/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Gestionar comentarios"));
    }

    @Test
    public void testObtenerPrivilegioPorId_noExistente() throws Exception {
        when(privilegiosService.obtenerPrivilegioPorId(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/privilegios/999"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testCrearPrivilegios() throws Exception {
        Privilegios nuevo = new Privilegios(null, "Ver stock", null);
        Privilegios guardado = new Privilegios(1L, "Ver stock", null);

        when(privilegiosService.creaPrivilegio(any(Privilegios.class))).thenReturn(guardado);

        mockMvc.perform(post("/api/v1/privilegios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(nuevo)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testActualizarPrivilegio_exito() throws Exception {
        Privilegios actualizado = new Privilegios(1L, "Actualizar permisos", null);

        when(privilegiosService.actualizarPrivilegio(eq(1L), any(Privilegios.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/v1/privilegios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(actualizado)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Actualizar permisos"));
    }

    @Test
    public void testActualizarPrivilegio_error() throws Exception {
        when(privilegiosService.actualizarPrivilegio(eq(999L), any(Privilegios.class)))
            .thenThrow(new RuntimeException("Privilegio no encontrado"));

        mockMvc.perform(put("/api/v1/privilegios/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(p1)))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testEliminarPrivilegio() throws Exception {
        doNothing().when(privilegiosService).eliminarPrivilegio(1L);

        mockMvc.perform(delete("/api/v1/privilegios/1"))
            .andExpect(status().isNoContent());
    }
}