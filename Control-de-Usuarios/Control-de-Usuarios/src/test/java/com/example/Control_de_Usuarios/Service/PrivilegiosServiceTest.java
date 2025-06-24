package com.example.Control_de_Usuarios.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.Control_de_Usuarios.Model.Privilegios;
import com.example.Control_de_Usuarios.Repository.PrivilegiosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PrivilegiosServiceTest {

    @Mock
    private PrivilegiosRepository privilegiosRepository;

    @InjectMocks
    private PrivilegiosService privilegiosService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerPrivilegios() {
        Privilegios p1 = new Privilegios(1L, "Ver productos", null);
        Privilegios p2 = new Privilegios(2L, "Eliminar productos", null);
        when(privilegiosRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Privilegios> resultado = privilegiosService.obtenerPrivilegios();
        assertEquals(2, resultado.size());
    }

    @Test
    public void testObtenerPrivilegioPorId_existente() {
        Privilegios privilegio = new Privilegios(1L, "Ver detalles", null);
        when(privilegiosRepository.findById(1L)).thenReturn(Optional.of(privilegio));

        Optional<Privilegios> resultado = privilegiosService.obtenerPrivilegioPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals("Ver detalles", resultado.get().getNombre());
    }

    @Test
    public void testCrearPrivilegio() {
        Privilegios privilegio = new Privilegios(null, "Agregar producto", null);
        Privilegios guardado = new Privilegios(1L, "Agregar producto", null);

        when(privilegiosRepository.save(privilegio)).thenReturn(guardado);

        Privilegios resultado = privilegiosService.creaPrivilegio(privilegio);
        assertNotNull(resultado.getId());
        assertEquals("Agregar producto", resultado.getNombre());
    }

    @Test
    public void testEliminarPrivilegio() {
        Long id = 1L;
        privilegiosService.eliminarPrivilegio(id);
        verify(privilegiosRepository, times(1)).deleteById(id);
    }

    @Test
    public void testActualizarPrivilegio_existente() {
        Privilegios existente = new Privilegios(1L, "Ver perfumes", null);
        Privilegios actualizado = new Privilegios(1L, "Ver reseñas", null);

        when(privilegiosRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(privilegiosRepository.save(any(Privilegios.class))).thenReturn(actualizado);

        Privilegios resultado = privilegiosService.actualizarPrivilegio(1L, actualizado);
        assertEquals("Ver reseñas", resultado.getNombre());
    }

    @Test
    public void testActualizarPrivilegio_noExistente() {
        when(privilegiosRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
            privilegiosService.actualizarPrivilegio(999L, new Privilegios()));
        assertTrue(ex.getMessage().contains("Privilegio no ecnontrado"));
    }
}