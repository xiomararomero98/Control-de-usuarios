package com.example.Control_de_Usuarios.Service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.Control_de_Usuarios.Model.Comuna;
import com.example.Control_de_Usuarios.Model.Region;
import com.example.Control_de_Usuarios.Repository.ComunaRepository;
import com.example.Control_de_Usuarios.Repository.RegionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

public class ComunaServiceTest {

    @Mock
    private ComunaRepository comunaRepository;

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private ComunaService comunaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerTodasLasComunas() {
        List<Comuna> comunas = Arrays.asList(new Comuna(), new Comuna());
        when(comunaRepository.findAll()).thenReturn(comunas);

        List<Comuna> resultado = comunaService.obtenerTodasLasComunas();

        assertEquals(2, resultado.size());
        verify(comunaRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerComunaPorId_Existente() {
        Comuna comuna = new Comuna();
        comuna.setId(1L);
        when(comunaRepository.findById(1L)).thenReturn(Optional.of(comuna));

        Optional<Comuna> resultado = comunaService.obtenerComunaPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
    }

    @Test
    public void testCrearComuna_Success() {
        Region region = new Region(1L, "Metropolitana", new ArrayList<>());
        when(regionRepository.findById(1L)).thenReturn(Optional.of(region));

        Comuna comuna = new Comuna(null, "Santiago", region, new ArrayList<>());
        when(comunaRepository.save(any(Comuna.class))).thenReturn(comuna);

        Comuna resultado = comunaService.crearComuna("Santiago", 1L);

        assertNotNull(resultado);
        assertEquals("Santiago", resultado.getNombre());
        verify(comunaRepository, times(1)).save(any(Comuna.class));
    }

    @Test
    public void testCrearComuna_RegionNoExiste() {
        when(regionRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> comunaService.crearComuna("Santiago", 1L));

        assertEquals("Región no encontrada con ID: 1", exception.getMessage());
    }

    @Test
    public void testActualizarComuna_Existente() {
        Region region = new Region(1L, "Valparaíso", new ArrayList<>());
        Comuna existente = new Comuna(1L, "Antiguo", region, new ArrayList<>());
        Comuna actualizada = new Comuna(1L, "Nuevo", region, new ArrayList<>());

        when(comunaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(regionRepository.findById(1L)).thenReturn(Optional.of(region));
        when(comunaRepository.save(any(Comuna.class))).thenReturn(actualizada);

        Comuna resultado = comunaService.actualizarComuna(1L, actualizada);

        assertEquals("Nuevo", resultado.getNombre());
        verify(comunaRepository, times(1)).save(any(Comuna.class));
    }

    @Test
    public void testActualizarComuna_NoExistente() {
        when(comunaRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> comunaService.actualizarComuna(1L, new Comuna()));

        assertEquals("Comuna no encontrada con ID: 1", exception.getMessage());
    }

    @Test
    public void testEliminarComunaPorId_Existente() {
        when(comunaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(comunaRepository).deleteById(1L);

        comunaService.eliminarComunaPorId(1L);

        verify(comunaRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testEliminarComunaPorId_NoExistente() {
        when(comunaRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> comunaService.eliminarComunaPorId(1L));

        assertEquals("Comuna no encontrada con ID: 1", exception.getMessage());
    }
}