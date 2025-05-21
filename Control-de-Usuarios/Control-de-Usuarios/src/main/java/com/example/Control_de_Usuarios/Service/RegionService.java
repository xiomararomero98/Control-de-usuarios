package com.example.Control_de_Usuarios.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Control_de_Usuarios.Model.Region;
import com.example.Control_de_Usuarios.Repository.RegionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    //Obtener todas las regiones

    public List <Region> obtenerRegiones(){
        return regionRepository.findAll();
    }

    //Obtener una region por ID

    public Optional <Region> obtenerRegionPorId(Long id){
        return regionRepository.findById(id);
    }

    //Crear una nueva region
    public Region crearRegion(Region region){
        return regionRepository.save(region);
    }

    //Actualizar una region existente 
    public Region actualizarRegion(Long id, Region regionActualizada){
        Region existente = regionRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("Region no encontrada con ID:"+id));
        existente.setNombre(regionActualizada.getNombre());
        return regionRepository.save(existente);
    }

    //eliminar region por ID
    public void eliminarRegionPorId(Long id){
        regionRepository.deleteById(id);
    }


}
