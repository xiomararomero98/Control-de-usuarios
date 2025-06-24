package com.example.Control_de_Usuarios.Service;

import com.example.Control_de_Usuarios.Model.Comuna;
import com.example.Control_de_Usuarios.Model.Direccion;
import com.example.Control_de_Usuarios.Model.Usuario;
import com.example.Control_de_Usuarios.Repository.ComunaRepository;
import com.example.Control_de_Usuarios.Repository.DireccionRepository;
import com.example.Control_de_Usuarios.Repository.UsuarioRepository;
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

class DireccionServiceTest {

    @Mock
    private DireccionRepository direccionRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ComunaRepository comunaRepository;

    @InjectMocks
    private DireccionService direccionService;

    private Usuario usuario;
    private Comuna comuna;
    private Direccion direccion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setId(1L);

        comuna = new Comuna();
        comuna.setId(1L);

        direccion = new Direccion();
        direccion.setId(1L);
        direccion.setCalle("Las Rosas");
        direccion.setNumeracion(123);
        direccion.setNumeroDepartamento(4);
        direccion.setTorre("A");
        direccion.setUsuario(usuario);
        direccion.setComuna(comuna);
    }

    @Test
    void testObtenerTodasLasDirecciones() {
        when(direccionRepository.findAll()).thenReturn(Arrays.asList(direccion));
        List<Direccion> resultado = direccionService.obtenerTodasLasDirecciones();
        assertEquals(1, resultado.size());
        verify(direccionRepository, times(1)).findAll();
    }

    @Test
    void testObtenerDireccionPorId() {
        when(direccionRepository.findById(1L)).thenReturn(Optional.of(direccion));
        Optional<Direccion> resultado = direccionService.obtenerDireccionPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals("Las Rosas", resultado.get().getCalle());
        verify(direccionRepository, times(1)).findById(1L);
    }

    @Test
    void testCrearDireccion() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(comunaRepository.findById(1L)).thenReturn(Optional.of(comuna));
        when(direccionRepository.save(any(Direccion.class))).thenReturn(direccion);

        Direccion resultado = direccionService.crearDireccion(direccion);
        assertNotNull(resultado);
        assertEquals("Las Rosas", resultado.getCalle());
        verify(direccionRepository).save(direccion);
    }

    @Test
    void testActualizarDireccion() {
        when(direccionRepository.findById(1L)).thenReturn(Optional.of(direccion));
        Direccion nuevosDatos = new Direccion(1L, "Nueva Calle", 456, 8, "B", usuario, comuna);
        when(direccionRepository.save(any(Direccion.class))).thenReturn(nuevosDatos);

        Direccion actualizada = direccionService.actualizarDireccion(1L, nuevosDatos);
        assertEquals("Nueva Calle", actualizada.getCalle());
        assertEquals(456, actualizada.getNumeracion());
        verify(direccionRepository).save(any(Direccion.class));
    }

    @Test
    void testEliminarDireccionPorId() {
        when(direccionRepository.existsById(1L)).thenReturn(true);
        doNothing().when(direccionRepository).deleteById(1L);

        assertDoesNotThrow(() -> direccionService.eliminarDireccionPorId(1L));
        verify(direccionRepository).deleteById(1L);
    }

    @Test
    void testEliminarDireccionPorId_noExiste() {
        when(direccionRepository.existsById(99L)).thenReturn(false);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            direccionService.eliminarDireccionPorId(99L);
        });
        assertEquals("Direccion no encontrada con ID:99", exception.getMessage());
    }
}