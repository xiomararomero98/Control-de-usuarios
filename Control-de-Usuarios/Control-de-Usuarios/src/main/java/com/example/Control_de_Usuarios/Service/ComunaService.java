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

    //obtener todas las comunas 
    public List<Comuna> obtenerTodasLasComunas(){
        return comunaRepository.findAll();
    }

    //Obtener comuna por Id
    public Optional<Comuna> obtenerComunaPorId(Long id){
        return comunaRepository.findById(id);
    }

    //Crear comuna
    
    public Comuna crearComuna(String nombre, Long idRegion){
        Region region = regionRepository.findById(idRegion)
        .orElseThrow(() -> new RuntimeException("Region no encontrada con ID:"+ idRegion));

        Comuna comuna = new Comuna();
        comuna.setNombre(nombre);
        comuna.setRegion(region);
        return comunaRepository.save(comuna);
    }

    //eliminar Comuna

    public void eliminarComunapoId(Long id){
        comunaRepository.deleteById(id);
    }

    //Actualizar comuna 

    public Comuna actualizarComuna(Long id, Comuna comunaActualizada){
        Optional <Comuna> optionalComuna = comunaRepository.findById(id);
        if (optionalComuna.isPresent()) {
            Comuna comunaExistente = optionalComuna.get();
            comunaExistente.setNombre(comunaActualizada.getNombre());
            comunaExistente.setRegion(comunaActualizada.getRegion());
            return comunaRepository.save(comunaExistente);
            
        } else {
            throw new RuntimeException("Comuna no encontrada con ID:"+id);
            
        }
    }



}
