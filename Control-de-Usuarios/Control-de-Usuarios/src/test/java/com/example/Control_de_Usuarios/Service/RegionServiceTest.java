package com.example.Control_de_Usuarios.Service;


import com.example.Control_de_Usuarios.Model.Region;
import com.example.Control_de_Usuarios.Repository.RegionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

public class RegionServiceTest {

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private RegionService regionService;

    private Region region;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        region = new Region(1L, "Metropolitana", null);
    }

    @Test
    void obtenerRegiones_deberiaRetornarLista() {
        when(regionRepository.findAll()).thenReturn(List.of(region));
        List<Region> resultado = regionService.obtenerRegiones();

        assertEquals(1, resultado.size());
        assertEquals("Metropolitana", resultado.get(0).getNombre());
        verify(regionRepository, times(1)).findAll();
    }

    @Test
    void obtenerRegionPorId_existente() {
        when(regionRepository.findById(1L)).thenReturn(Optional.of(region));
        Optional<Region> resultado = regionService.obtenerRegionPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Metropolitana", resultado.get().getNombre());
        verify(regionRepository).findById(1L);
    }

    @Test
    void obtenerRegionPorId_noExiste() {
        when(regionRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Region> resultado = regionService.obtenerRegionPorId(99L);

        assertFalse(resultado.isPresent());
        verify(regionRepository).findById(99L);
    }

    @Test
    void crearRegion_deberiaGuardarYRetornarRegion() {
        Region nueva = new Region(null, "Valparaíso", null);
        Region guardada = new Region(2L, "Valparaíso", null);

        when(regionRepository.save(nueva)).thenReturn(guardada);
        Region resultado = regionService.crearRegion(nueva);

        assertNotNull(resultado.getId());
        assertEquals("Valparaíso", resultado.getNombre());
        verify(regionRepository).save(nueva);
    }

    @Test
    void actualizarRegion_existente() {
        Region actualizada = new Region(null, "Actualizada", null);
        Region existente = new Region(1L, "Antigua", null);

        when(regionRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(regionRepository.save(any(Region.class))).thenAnswer(i -> i.getArgument(0));

        Region resultado = regionService.actualizarRegion(1L, actualizada);

        assertEquals("Actualizada", resultado.getNombre());
        verify(regionRepository).save(existente);
    }

    @Test
    void actualizarRegion_noExiste_deberiaLanzarExcepcion() {
        Region regionNueva = new Region(null, "Nueva", null);
        when(regionRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                regionService.actualizarRegion(99L, regionNueva));

        assertEquals("Region no encontrada con ID:99", ex.getMessage());
        verify(regionRepository, never()).save(any());
    }

    @Test
    void eliminarRegionPorId_deberiaEliminar() {
        doNothing().when(regionRepository).deleteById(1L);
        regionService.eliminarRegionPorId(1L);

        verify(regionRepository).deleteById(1L);
    }
}
