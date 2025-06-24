package com.example.Control_de_Usuarios.Service;


import com.example.Control_de_Usuarios.Model.Rol;
import com.example.Control_de_Usuarios.Repository.RolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RolServiceTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService rolService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerRoles() {
        Rol rol1 = new Rol(1L, "ADMIN");
        Rol rol2 = new Rol(2L, "CLIENTE");

        when(rolRepository.findAll()).thenReturn(Arrays.asList(rol1, rol2));

        List<Rol> resultado = rolService.obtenerRoles();

        assertEquals(2, resultado.size());
        verify(rolRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerRolPorId_existente() {
        Rol rol = new Rol(1L, "LOGISTICA");

        when(rolRepository.findById(1L)).thenReturn(Optional.of(rol));

        Rol resultado = rolService.obtenerRolPorId(1L);

        assertNotNull(resultado);
        assertEquals("LOGISTICA", resultado.getNombre());
    }

    @Test
    public void testObtenerRolPorId_noExistente() {
        when(rolRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> rolService.obtenerRolPorId(1L));
        assertEquals("Rol no encontrado con ID:1", ex.getMessage());
    }

    @Test
    public void testCrearRol() {
        Rol rol = new Rol(1L, "GERENTE");

        when(rolRepository.save(rol)).thenReturn(rol);

        Rol resultado = rolService.crearRol(rol);

        assertNotNull(resultado);
        assertEquals("GERENTE", resultado.getNombre());
        verify(rolRepository, times(1)).save(rol);
    }

    @Test
    public void testEliminarRol() {
        doNothing().when(rolRepository).deleteById(1L);

        rolService.eliminarRol(1L);

        verify(rolRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testActualizarRol_existente() {
        Rol original = new Rol(1L, "CLIENTE");
        Rol actualizado = new Rol(1L, "ACTUALIZADO");

        when(rolRepository.findById(1L)).thenReturn(Optional.of(original));
        when(rolRepository.save(any(Rol.class))).thenReturn(actualizado);

        Rol resultado = rolService.actualizarRol(1L, actualizado);

        assertEquals("ACTUALIZADO", resultado.getNombre());
    }

    @Test
    public void testActualizarRol_noExistente() {
        Rol nuevo = new Rol(1L, "GERENCIA");

        when(rolRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> rolService.actualizarRol(1L, nuevo));
        assertEquals("Rol no encontrado con ID1", ex.getMessage());
    }
}