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

import com.example.Control_de_Usuarios.Model.Privilegios;
import com.example.Control_de_Usuarios.Service.PrivilegiosService;

@RestController
@RequestMapping("/api/v1/privilegios")
public class PrivilegiosController {

    @Autowired
    private PrivilegiosService privilegiosService;

    //obtener todos los privilegios

    @GetMapping
    public ResponseEntity<List<Privilegios>> obtenerPrivilegios(){
        List<Privilegios> lista = privilegiosService.obtenerPrivilegios();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
            
        }
        return ResponseEntity.ok(lista);
    }
    
    //obtener privilegio por ID
    @GetMapping("/{id}")
    public ResponseEntity<Privilegios> obtenerPrivilegioPorId(@PathVariable Long id){
        Optional<Privilegios> privilegio = privilegiosService.obtenerPrivilegioPorId(id);
        if (privilegio.isPresent()) {
            return ResponseEntity.ok(privilegio.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //crear nuevo privilegio
    @PostMapping
    public ResponseEntity<Privilegios> crearPrivilegios(@RequestBody Privilegios privilegio){
        Privilegios nuevo = privilegiosService.creaPrivilegio(privilegio);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    //Actualizar privilegio

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPrivilegios(@PathVariable Long id, @RequestBody Privilegios privilegioActualizado){
        try {
            Privilegios actualizado= privilegiosService.actualizarPrivilegio(id, privilegioActualizado);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //eliminar privilegio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrivilegio(@PathVariable Long id ){
        privilegiosService.eliminarPrivilegio(id);
        return ResponseEntity.noContent().build();
    }



}
