package com.example.Control_de_Usuarios.Controller;

import com.example.Control_de_Usuarios.Model.Rol;
import com.example.Control_de_Usuarios.Model.Usuario;
import com.example.Control_de_Usuarios.Service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(UsuarioController.class)
@ExtendWith(SpringExtension.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void obtenerUsuarios_devuelveListaUsuarios() throws Exception {
        Rol rol = new Rol(1L, "ADMIN");
        Usuario usuario = new Usuario(1L, "Juan", "Pérez", "juan@test.com", "clave123", new Date(), rol, new ArrayList<>());
        when(usuarioService.obtenerUsuarios()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].correo").value("juan@test.com"));
    }

    @Test
    void obtenerUsuarioPorId_usuarioExiste() throws Exception {
        Rol rol = new Rol(1L, "ADMIN");
        Usuario usuario = new Usuario(1L, "Juan", "Pérez", "juan@test.com", "clave123", new Date(), rol, new ArrayList<>());
        when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    
    @Test
    void crearUsuario_retornaUsuarioCreado() throws Exception {
        Rol rol = new Rol(1L, "ADMIN");
        Usuario usuario = new Usuario(null, "Ana", "Lopez", "ana@test.com", "clave456", new Date(), rol, new ArrayList<>());
        Usuario creado = new Usuario(2L, "Ana", "Lopez", "ana@test.com", "clave456", new Date(), rol, new ArrayList<>());

        when(usuarioService.crearUsuario("Ana", "Lopez", "ana@test.com", "clave456", 1L)).thenReturn(creado);

        mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2));
    }


    @Test
    void actualizarUsuario_usuarioExiste() throws Exception {
        Rol rol = new Rol(1L, "CLIENTE");
        Usuario actualizado = new Usuario(1L, "Pedro", "Gomez", "pedro@test.com", "nuevaClave", new Date(), rol, new ArrayList<>());

        when(usuarioService.actualizarUsuario(any(Long.class), any(Usuario.class)))
                .thenReturn(Optional.of(actualizado));

        mockMvc.perform(put("/api/v1/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Pedro"));
    }

    @Test
    void eliminarUsuario_sinErrores() throws Exception {
        doNothing().when(usuarioService).eliminarUsuario(1L);

        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void obtenerUsuarioPorCorreo_usuarioExiste() throws Exception {
        Rol rol = new Rol(1L, "ADMIN");
        Usuario usuario = new Usuario(1L, "Sofia", "Ramirez", "sofia@test.com", "clave789", new Date(), rol, new ArrayList<>());

        when(usuarioService.findByCorreo("sofia@test.com")).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/v1/usuarios/correo/sofia@test.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correo").value("sofia@test.com"));
    }

    @Test
    void obtenerUsuarioPorId_usuarioNoExiste() throws Exception {
        when(usuarioService.obtenerUsuarioPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/usuarios/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerUsuarioPorCorreo_usuarioNoExiste() throws Exception {
        when(usuarioService.findByCorreo("desconocido@test.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/usuarios/correo/desconocido@test.com"))
                .andExpect(status().isNotFound());
    }
}