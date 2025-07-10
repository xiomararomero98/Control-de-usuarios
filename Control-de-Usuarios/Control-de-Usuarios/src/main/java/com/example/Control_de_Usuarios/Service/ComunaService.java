package com.example.Control_de_Usuarios.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Control_de_Usuarios.Model.Comuna;
import com.example.Control_de_Usuarios.Model.Region;
import com.example.Control_de_Usuarios.Repository.ComunaRepository;
import com.example.Control_de_Usuarios.Repository.RegionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ComunaService {

    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private RegionRepository regionRepository;

    public List<Comuna> obtenerTodasLasComunas() {
        return comunaRepository.findAll();
    }

    public Optional<Comuna> obtenerComunaPorId(Long id) {
        return comunaRepository.findById(id);
    }

    public Comuna crearComuna(String nombre, Long idRegion) {
        Region region = regionRepository.findById(idRegion)
                .orElseThrow(() -> new RuntimeException("Región no encontrada con ID: " + idRegion));

        Comuna comuna = new Comuna();
        comuna.setNombre(nombre);
        comuna.setRegion(region);
        return comunaRepository.save(comuna);
    }

    public Comuna actualizarComuna(Long id, Comuna comunaActualizada) {
        Comuna existente = comunaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comuna no encontrada con ID: " + id));

        if (comunaActualizada.getRegion() == null || comunaActualizada.getRegion().getId() == null) {
            throw new RuntimeException("Debe incluir una región válida para actualizar la comuna");
        }

        Region region = regionRepository.findById(comunaActualizada.getRegion().getId())
                .orElseThrow(() -> new RuntimeException("Región no encontrada con ID: " + comunaActualizada.getRegion().getId()));

        existente.setNombre(comunaActualizada.getNombre());
        existente.setRegion(region);
        return comunaRepository.save(existente);
    }

    public void eliminarComunaPorId(Long id) {
        if (!comunaRepository.existsById(id)) {
            throw new RuntimeException("Comuna no encontrada con ID: " + id);
        }
        comunaRepository.deleteById(id);
    }
}