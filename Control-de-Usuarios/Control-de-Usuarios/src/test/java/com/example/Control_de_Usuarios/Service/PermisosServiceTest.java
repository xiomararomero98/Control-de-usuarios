package com.example.Control_de_Usuarios.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.Control_de_Usuarios.Model.Permisos;
import com.example.Control_de_Usuarios.Model.Privilegios;
import com.example.Control_de_Usuarios.Model.Rol;
import com.example.Control_de_Usuarios.Repository.PermisosRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class PermisosServiceTest {

    @Mock
    private PermisosRepository permisosRepository;

    @InjectMocks
    private PermisosService permisosService;

    private Permisos permiso;
    private Privilegios privilegio;
    private Rol rol;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        privilegio = new Privilegios();
        privilegio.setId(1L);
        privilegio.setNombre("Ver catálogo");

        rol = new Rol();
        rol.setId(1L);
        rol.setNombre("Administrador");

        permiso = new Permisos();
        permiso.setId(1L);
        permiso.setPrivilegio(privilegio);
        permiso.setRol(rol);
    }

    @Test
    void testObtenerPermisos() {
        List<Permisos> lista = Arrays.asList(permiso);
        when(permisosRepository.findAll()).thenReturn(lista);

        List<Permisos> resultado = permisosService.obtenerPermisos();
        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
    }

    @Test
    void testObtenerPermisoPorId_existente() {
        when(permisosRepository.findById(1L)).thenReturn(Optional.of(permiso));

        Permisos resultado = permisosService.obtenerPermisoPorId(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void testObtenerPermisoPorId_noExistente() {
        when(permisosRepository.findById(2L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, () -> {
            permisosService.obtenerPermisoPorId(2L);
        });
        assertTrue(ex.getMessage().contains("Permiso no encontrado"));
    }

    @Test
    void testCrearPermisos() {
        when(permisosRepository.save(permiso)).thenReturn(permiso);

        Permisos creado = permisosService.crearPermisos(permiso);
        assertNotNull(creado);
        assertEquals(1L, creado.getId());
    }

    @Test
    void testActualizarPermiso_existente() {
        Permisos actualizado = new Permisos();
        actualizado.setPrivilegio(privilegio);
        actualizado.setRol(rol);

        when(permisosRepository.findById(1L)).thenReturn(Optional.of(permiso));
        when(permisosRepository.save(any(Permisos.class))).thenReturn(permiso);

        Permisos result = permisosService.actualizarPermiso(1L, actualizado);
        assertNotNull(result);
        assertEquals(privilegio, result.getPrivilegio());
    }

    @Test
    void testActualizarPermiso_noExistente() {
        when(permisosRepository.findById(999L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, () -> {
            permisosService.actualizarPermiso(999L, permiso);
        });
        assertTrue(ex.getMessage().contains("Permiso no encontrado"));
    }

    @Test
    void testEliminarPermisoPorId() {
        doNothing().when(permisosRepository).deleteById(1L);
        assertDoesNotThrow(() -> permisosService.eliminarPermisoPorId(1L));
        verify(permisosRepository, times(1)).deleteById(1L);
    }
}