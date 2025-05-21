package com.example.Control_de_Usuarios.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Control_de_Usuarios.Model.Region;
import com.example.Control_de_Usuarios.Service.RegionService;

@RestController
@RequestMapping("/api/v1/regiones")
public class RegionController {

    @Autowired
    private RegionService regionService;

    //obtener todas las regiones
    @GetMapping
    public ResponseEntity<List<Region>> obtenerRegiones(){
        List <Region> regiones = regionService.obtenerRegiones();
        if (regiones.isEmpty()) {
            return ResponseEntity.noContent().build();
            
        }
        return ResponseEntity.ok(regiones);
    }

    //Obtener region por Id
    @GetMapping("/{id}")
    public ResponseEntity<Region> obtenerRegionPorId(@PathVariable Long id){
        Optional<Region> region =regionService.obtenerRegionPorId(id);
        if (region.isPresent()) {
            return ResponseEntity.ok(region.get());
            
        } else {
            return ResponseEntity.notFound().build();
            
        }



    }
    //Crear nueva region
    @PostMapping
    public ResponseEntity<Region> crearRegion(@RequestBody Region region){
        Region nueva = regionService.crearRegion(region);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }
    //actualizar una region existente
    @PutMapping("/{id}")
    public ResponseEntity <?> actualizarRegion(@PathVariable Long id, @RequestBody Region regionActualizada){
        try {
            Region actualizada = regionService.actualizarRegion(id, regionActualizada);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //eliminar una region 
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarRegion(@PathVariable Long id){
        try {
            regionService.eliminarRegionPorId(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



}
