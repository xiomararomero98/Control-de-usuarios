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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Control_de_Usuarios.Model.Direccion;
import com.example.Control_de_Usuarios.Service.DireccionService;

@RestController
@RequestMapping("/api/v1/direcciones")
public class DireccionController {
    @Autowired
    private DireccionService direccionService;

    //obtener todas las direcciones
    @GetMapping
    public ResponseEntity<List<Direccion>> obtenerDirecciones(){
        List<Direccion> direcciones = direccionService.obtenerTodasLasDirecciones();
        if (direcciones.isEmpty()) {
            return ResponseEntity.noContent().build();
            
        }
        return ResponseEntity.ok(direcciones);
    }

    //Obtener direccion por ID
    @GetMapping("/{id}")
    public ResponseEntity<Direccion> obtenerDireccionPorId(@PathVariable Long id){
        Optional<Direccion> direccion = direccionService.obtenerDireccionPorId(id);
        if (direccion.isPresent()) {
            return ResponseEntity.ok(direccion.get());
            
        } else {
            return ResponseEntity.notFound().build();
            
        }

    
    }
    //Crear direccion
    @PostMapping
    public ResponseEntity<?> crearDireccion(
    @RequestParam String calle,
    @RequestParam Integer numeracion,
    @RequestParam(required = false) Integer numeroDepartamento,
    @RequestParam(required = false) String torre,
    @RequestParam Long idUsuario,
    @RequestParam Long idComuna) {
    try {
        Direccion nueva = direccionService.crearDireccion(
            calle, numeracion, numeroDepartamento, torre, idUsuario, idComuna
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
    //Actualizar direccion
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDireccion(@PathVariable Long id, @RequestBody Direccion datos){
        try {
            Direccion actualizada = direccionService.actualizarDireccion(id, datos);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //eliminar direccion
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDireccion(@PathVariable Long id){
        try {
            direccionService.eliminarDireccionPorId(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
            
        }
    }





    

    


}
