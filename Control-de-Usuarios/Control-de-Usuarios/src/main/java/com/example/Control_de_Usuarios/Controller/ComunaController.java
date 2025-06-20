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

import com.example.Control_de_Usuarios.Model.Comuna;
import com.example.Control_de_Usuarios.Service.ComunaService;

@RestController
@RequestMapping("/api/v1/comunas")
public class ComunaController {
    
    @Autowired
    private ComunaService comunaService;

    //obtener todas las comunas

    @GetMapping
    public ResponseEntity<List<Comuna>> obtenerComunas(){
        List<Comuna> comunas = comunaService.obtenerTodasLasComunas();
        if (comunas.isEmpty()) {
            return ResponseEntity.noContent().build();
            
        }
        
        return ResponseEntity.ok(comunas);
    }

    //obtener comunas por id
    @GetMapping("/{id}")
    public ResponseEntity <Comuna> obtenerComunaPorId(@PathVariable Long id){
        Optional <Comuna> comuna = comunaService.obtenerComunaPorId(id);
        if (comuna.isPresent()) {
            return ResponseEntity.ok(comuna.get());
            
        } else {
            return ResponseEntity.notFound().build();
            
        }

    }

    //crear nueva comuna
    @PostMapping
    public ResponseEntity<?> crearComuna(@RequestBody Comuna comuna){
    try {
        System.out.println("➡️ Nombre recibido: " + comuna.getNombre());
        Long idRegion= comuna.getRegion().getId();
        Comuna nueva = comunaService.crearComuna(comuna.getNombre(), idRegion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}

    //Actualizar comuna
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarComuna(@PathVariable Long id, @RequestBody Comuna datos){
        try {
            Comuna actualizada = comunaService.actualizarComuna(id, datos);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //eliminar comuna
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarComuna(@PathVariable Long id){
        try {
            comunaService.eliminarComunapoId(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }





}
